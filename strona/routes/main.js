const express = require('express');
const mainController = require('../controllers/main');
const router = express.Router();

router.get('/main', mainController.getRecipes); 
router.put('/main', mainController.updateDayWithRecipe);
router.put('/delete-day/:dayNumber', mainController.deleteRecipeFromDay);
router.delete('/delete-products-from-shopping-list/:recipeId', mainController.deleteProductsFromShoppingList);
router.post('/shopping-list', mainController.addProductIntoShoppingList);

module.exports = router;
