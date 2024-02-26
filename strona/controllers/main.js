const mysql = require("mysql");

const db = mysql.createConnection({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    port: process.env.DATABASE_PORT,
    password: process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE
});

exports.getRecipes = (req, res) => {
    const queryRecipes = 'SELECT id_przepisy, nazwa, sposob_przygotowania FROM przepisy';
    const queryDays = ' SELECT dzien.*, przepisy.id_przepisy AS id_przepisu, przepisy.nazwa AS nazwa_przepisu, przepisy.sposob_przygotowania AS sposob_przygotowania_przepisu FROM dzien LEFT JOIN przepisy ON dzien.przepisy_id_przepisy = przepisy.id_przepisy ORDER BY dzien.id_dzien';

    db.query(queryRecipes, (errorRecipes, resultsRecipes) => {
        if (errorRecipes) {
            console.log(errorRecipes);
            return res.status(500).json({ error: 'Błąd podczas pobierania przepisów z bazy danych.' });
        }

        db.query(queryDays, (errorDays, resultsDays) => {
            if (errorDays) {
                console.log(errorDays);
                return res.status(500).json({ error: 'Błąd podczas pobierania dni z bazy danych.' });
            }

            console.log(resultsRecipes);
            console.log(resultsDays);

            // Przesyłamy przepisy i dni jako odpowiedź w formacie JSON
            res.json({ recipes: resultsRecipes, days: resultsDays });
        });
    });
}

exports.updateDayWithRecipe = (req, res) => {
    const { dayId, recipeId } = req.body;

    console.log('Updating day:', dayId, 'with recipe:', recipeId);
    
    const updateQuery = `
        UPDATE dzien
        SET przepisy_id_przepisy = ?
        WHERE id_dzien = ?;
    `;

    db.query(updateQuery, [recipeId, dayId], (error, result) => {
        if (error) {
            console.error('Error updating day with recipe:', error);
            return res.status(500).json({ error: 'Błąd podczas aktualizacji dnia z przepisem w bazie danych.' });
        }

        console.log('Day updated with recipe:', result);
        res.json({ success: true });
    });
};


exports.deleteRecipeFromDay = (req, res) => {
    const { dayNumber  } = req.params;

    console.log('Deleting recipe from day:', dayNumber );
    
    const updateQuery = `
        UPDATE dzien
        SET przepisy_id_przepisy = NULL
        WHERE id_dzien = ?;
    `;

    db.query(updateQuery, [dayNumber ], (error, result) => {
        if (error) {
            console.error('Error updating day with recipe:', error);
            return res.status(500).json({ error: 'Błąd podczas aktualizacji dnia z przepisem w bazie danych.' });
        }

        console.log('Day updated with recipe:', result);
        res.json({ success: true });
    });
};

exports.addProductIntoShoppingList = (req, res) => {
    const { przepisyId } = req.body;

    // Sprawdź, czy przepis już istnieje na liście zakupów
    const checkQuery = `
    SELECT 
        lz.id_lista_zakupow, 
        lz.ilosc AS ilosc_lista, 
        pp.ilosc AS ilosc_przepisy
    FROM lista_zakupow lz
    JOIN przepisy_produkty pp ON lz.przepisy_produkty_id_przepisy_produkty = pp.id_przepisy_produkty
    WHERE pp.przepisy_id_przepisy = ?;
`;


    db.query(checkQuery, [przepisyId], (checkError, checkResults) => {
        if (checkError) {
            console.error('Error checking existing products:', checkError);
            return res.status(500).json({ error: 'Błąd podczas sprawdzania istniejących produktów na liście zakupów.' });
        }

        if (checkResults.length > 0) {
            // Jeśli przepis już istnieje na liście zakupów, zaktualizuj tylko ilość
            
         //   const { id_lista_zakupow, ilosc } = checkResults[0];
            
            const updateQuery = `
                UPDATE lista_zakupow
                SET ilosc = ?
                WHERE id_lista_zakupow = ?;
            `;
            checkResults.forEach(row => {
                const{ id_lista_zakupow, ilosc_lista, ilosc_przepisy} = row;
            db.query(updateQuery, [ilosc_lista + ilosc_przepisy, id_lista_zakupow], (updateError, updateResult) => {
                if (updateError) {
                    console.error('Error updating quantity:', updateError);
                    return res.status(500).json({ error: 'Błąd podczas aktualizacji ilości na liście zakupów.' });
                }

                
            });
        });
        console.log('Quantity updated in shopping list');
                res.json({ success: true });
        } else {
            // Jeśli przepis nie istnieje, dodaj nowy wpis
            const insertQuery = `
                INSERT INTO lista_zakupow (ilosc, przepisy_produkty_id_przepisy_produkty)
                SELECT ilosc, id_przepisy_produkty
                FROM przepisy_produkty
                WHERE przepisy_id_przepisy = ?;
            `;

            db.query(insertQuery, [przepisyId], (insertError, insertResult) => {
                if (insertError) {
                    console.error('Error adding new product:', insertError);
                    return res.status(500).json({ error: 'Błąd podczas dodawania nowego produktu do listy zakupów.' });
                }

                console.log('Product added to shopping list');
                res.json({ success: true });
            });
        }
    });
};



exports.deleteProductsFromShoppingList = (req, res) => {
    const { recipeId } = req.params;

    // Sprawdź, czy istnieją już produkty na liście zakupów
    const checkQuery = `
    SELECT 
    lz.id_lista_zakupow, 
    lz.ilosc AS ilosc_lista, 
    pp.ilosc AS ilosc_przepisy
FROM lista_zakupow lz
JOIN przepisy_produkty pp ON lz.przepisy_produkty_id_przepisy_produkty = pp.id_przepisy_produkty
WHERE pp.przepisy_id_przepisy = ?;
    `;

    db.query(checkQuery, [recipeId], (checkError, checkResults) => {
        if (checkError) {
            console.error('Error checking existing products:', checkError);
            return res.status(500).json({ error: 'Błąd podczas sprawdzania istniejących produktów na liście zakupów.' });
        }

        // Iteruj przez wyniki sprawdzenia
        checkResults.forEach((row) => {
            const { id_lista_zakupow, ilosc_lista, ilosc_przepisy } = row;

            // Aktualizuj ilość
            const updateQuery = `
                UPDATE lista_zakupow
                SET ilosc = ?
                WHERE id_lista_zakupow = ?;
            `;

            db.query(updateQuery, [ilosc_lista - ilosc_przepisy, id_lista_zakupow], (updateError, updateResult) => {
                if (updateError) {
                    console.error('Error updating quantity:', updateError);
                    return res.status(500).json({ error: 'Błąd podczas aktualizacji ilości na liście zakupów.' });
                }

                // Jeśli ilość wynosi 0 lub mniej, usuń produkt
                if (ilosc_lista - ilosc_przepisy <= 0) {
                    const deleteQuery = `
                        DELETE FROM lista_zakupow
                        WHERE id_lista_zakupow = ?;
                    `;

                    db.query(deleteQuery, [id_lista_zakupow], (deleteError, deleteResult) => {
                        if (deleteError) {
                            console.error('Error deleting product from shopping list:', deleteError);
                            return res.status(500).json({ error: 'Błąd podczas usuwania produktu z listy zakupów.' });
                        }

                        console.log('Product deleted from shopping list:', deleteResult);
                    });
                }
            });
        });

        console.log('Products updated in shopping list');
        res.json({ success: true });
    });
};
