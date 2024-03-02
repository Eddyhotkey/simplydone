var http = require('http');
const express = require('express');
const app = express();

app.get('/', (req, res) => {
  res.send('Hello, iam the root page');
});

// Include route files
const usersRoute = require('./controllers/users');
const todoRoute = require('./controllers/todo');
const categoryRoute = require('./controllers/category');

// Use routes
app.use('/users', usersRoute);
app.use('/todo', todoRoute);
app.use('/category', categoryRoute);

const port = 1337;

app.listen(port, () => {
    console.log('Server is running at port '+ port);
});