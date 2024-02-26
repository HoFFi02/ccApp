const mysql = require("mysql");

const db = mysql.createConnection({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    port: process.env.DATABASE_PORT,
    password: process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE
});

// controllers/recipe.js
exports.getRecipes = (req, res) => {
  const queryRecipes = `
  SELECT
    przepisy.id_przepisy,
    przepisy.nazwa as nazwa_przepisu,
    przepisy.sposob_przygotowania,
    produkty.id_produkty,
    GROUP_CONCAT(CONCAT(produkty.nazwa, ' ', przepisy_produkty.ilosc, ' ', produkty.jednostka) SEPARATOR ', ') AS skladniki
FROM
    przepisy
LEFT JOIN
    przepisy_produkty ON przepisy.id_przepisy = przepisy_produkty.przepisy_id_przepisy
LEFT JOIN
    produkty ON przepisy_produkty.produkty_id_produkty = produkty.id_produkty
GROUP BY
    przepisy.id_przepisy;

`;

    db.query(queryRecipes, (errorRecipes, resultsRecipes) => {
        if (errorRecipes) {
            console.error(errorRecipes);
            return res.status(500).json({ error: 'Błąd podczas pobierania przepisów z bazy danych.' });
        }

        const recipes = [];
        let currentRecipe = null;

        resultsRecipes.forEach(row => {
            if (!currentRecipe || currentRecipe.id_przepisy !== row.id_przepisy) {
                // Nowy przepis
                if (currentRecipe) {
                    recipes.push(currentRecipe);
                }

                currentRecipe = {
                    id_przepisy: row.id_przepisy,
                    nazwa: row.nazwa_przepisu,
                    sposob_przygotowania: row.sposob_przygotowania,
                    products: []
                };
            }

            // Dodaj produkt z aktualnego wiersza
            if (row.id_produkty) {
                currentRecipe.products.push({
                    skladniki: row.skladniki
                });
            }
        });

        // Dodaj ostatni przepis
        if (currentRecipe) {
            recipes.push(currentRecipe);
        }

        console.log(recipes);
        res.json({ recipes });
    });
}


