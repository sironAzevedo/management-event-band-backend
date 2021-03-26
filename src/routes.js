const express = require("express");
const routes = express.Router();
const UserController = require("./controllers/UserController");
const BandController = require("./controllers/BandController");

routes.get('/', (req, res) => {
    res.status(200);
    return res.json({hello: 'World'});
});

//Route User
routes.post('/users', UserController.store);
routes.get('/users', UserController.index);
routes.put('/users/:user_id', UserController.update);
routes.delete('/users/:user_id', UserController.delete);

//Route Band
routes.post('/users/:user_id/bands', BandController.store);
routes.get('/users/:user_id/bands', BandController.index);
routes.delete('/users/:user_id/bands', BandController.delete);


module.exports = routes;