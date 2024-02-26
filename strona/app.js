const express =require("express");
const path = require('path');
const mysql = require("mysql");
const dotenv = require('dotenv');

dotenv.config({ path: './.env'});

const app = express();

const db = mysql.createConnection({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    port: process.env.DATABASE_PORT,
    password: process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE
});

const publicDirectory = path.join(__dirname, './public');
app.use(express.static(publicDirectory));

app.use(express.urlencoded({ extended: false}));
app.use(express.json());

app.set('view engine', 'hbs');
app.use(express.static(path.join(__dirname, 'public')));

db.connect( (error) => {
    if(error){
    console.log(error)
    }else {
        console.log("MYSQL Connected...")
    }
} )

//Define Routes
app.use('/', require('./routes/pages'));
app.use('/auth', require('./routes/auth'));
app.use('/login', require('./routes/login'));
app.use('/main', require('./routes/main'))
app.use('/recipe', require('./routes/recipe'))
app.use('/lista_zakupow', require('./routes/lista_zakupow'))
app.use('/scripts', express.static('scripts'));


app.listen(5000, () => {
    console.log("Server started on Port 5000")
})