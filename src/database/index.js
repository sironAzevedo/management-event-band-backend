const Sequelize= require('sequelize');
const dbConfig = require('../config/database');
const Bands = require('../models/Bands');

const User = require('../models/User');

const connection = new Sequelize(dbConfig);

User.init(connection);
Bands.init(connection);

module.exports = connection;