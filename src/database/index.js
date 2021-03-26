const Sequelize= require('sequelize');
const dbConfig = require('../config/database');
const Band = require('../models/Band');
const User = require('../models/User');

const connection = new Sequelize(dbConfig);

User.init(connection);
Band.init(connection);

User.associate(connection.models);
Band.associate(connection.models)


module.exports = connection;