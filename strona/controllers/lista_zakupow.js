const mysql = require("mysql");

const db = mysql.createConnection({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    port: process.env.DATABASE_PORT,
    password: process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE
});

// controllers/recipe.js
exports.getShoppingList = (req, res) => {
    const queryShoppingList = `
        SELECT
            lista_zakupow.przepisy_produkty_id_przepisy_produkty,
            produkty.nazwa AS nazwa_skladnika,
            lista_zakupow.ilosc,
            produkty.jednostka
        FROM
            lista_zakupow
        JOIN
            przepisy_produkty ON lista_zakupow.przepisy_produkty_id_przepisy_produkty = przepisy_produkty.id_przepisy_produkty
        JOIN
            produkty ON przepisy_produkty.produkty_id_produkty = produkty.id_produkty;
    `;

    db.query(queryShoppingList, (error, results) => {
        if (error) {
            console.error(error);
            return res.status(500).json({ error: 'Błąd podczas pobierania listy zakupów z bazy danych.' });
        }

        const shoppingList = results.map(row => ({
            przepisy_produkty_id_przepisy_produkty: row.przepisy_produkty_id_przepisy_produkty,
            nazwa_skladnika: row.nazwa_skladnika,
            ilosc: row.ilosc,
            jednostka: row.jednostka
        }));

        console.log(shoppingList);
        res.json({ shoppingList });
    });
}



