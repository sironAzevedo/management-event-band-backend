const express = require("express");
const app = express();

require('./database');

const cors = require("cors");
const routes = require('./routes');

app.use(cors());
app.use(express.json());
app.use(routes);

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Server running on ${port}, http://localhost:${port}`));