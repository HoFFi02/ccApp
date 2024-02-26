const express = require('express');
const logController = require('../controllers/login');
const router = express.Router();

router.post('/login', logController.login)


module.exports = router;