const express = require('express');
const logController = require('../controllers/recipe');
const router = express.Router();

router.get('/recipe', logController.getRecipes)


module.exports = router;