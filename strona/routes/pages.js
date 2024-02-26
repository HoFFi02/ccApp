const express = require('express');

const router = express.Router();

router.get('/', (req,res) => {
    res.render('index');
});
router.get('/register', (req,res) => {
    res.render('register');
});
router.get('/login', (req,res) => {
    res.render('login');
});
router.get('/main', (req,res) => {
    res.render('main'); 
});
router.get('/recipe', (req,res) => {
    res.render('recipe');
});
router.get('/lista_zakupow', (req,res) => {
    res.render('lista_zakupow');
});



module.exports = router;