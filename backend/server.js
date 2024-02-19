var http = require('http');
const express = require('express');
const app = express();

app.get('/', (req, res) => {
  res.send('Hello, iam the root page');
});

// Include route files
const usersRoute = require('./controllers/users');
//const productsRoute = require('./routes/products');

// Use routes
app.use('/users', usersRoute);
//app.use('products', productsRoute);

const port = 1337;

app.listen(port, () => {
    console.log('Server is running at port '+ port);
});