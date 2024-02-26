const mysql = require("mysql");
const bcrypt = require('bcryptjs');

const db = mysql.createConnection({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    port: process.env.DATABASE_PORT,
    password: process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE
});


exports.login = (req, res) => {
  console. log(req.body);
  
  const { email, password} = req.body;

  db.query('SELECT email FROM uzytkownicy WHERE email = ?', [email], async (error, results) =>{
    if(error){
        console.log(error);
    }

    if(results.length === 0){
        return res.render('login', {
            message: 'That email is not in database'
        })
    } 

    db.query('SELECT password FROM uzytkownicy WHERE email=?', [email], (error, results) =>{
        if(error){
            console.log(error);
        }
        bcrypt.compare(password, results[0].password, (error, isMatch) => {
            if (error) {
                console.log(error);
                return res.render('login', {
                    message: 'An error occurred while checking the password'
                });
            }
        
            if (!isMatch) {
                return res.render('login', {
                    message: 'Incorrect password or incorrect email'
                });
            } else {
                res.redirect('/main');
            }
        });
    })


  });

 
}