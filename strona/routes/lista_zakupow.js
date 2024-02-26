const express = require('express');
const logController = require('../controllers/lista_zakupow');
const router = express.Router();

router.get('/lista_zakupow', logController.getShoppingList)


module.exports = router;