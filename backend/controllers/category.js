// routes/users.js
const express = require('express');
const dbConnection = require('../database');
const router = express.Router();

// Define a route
router.get('/', async (req, res) => {
    let response = '';
    response += 'Verfügbare Routen: <br><br>';

    response += '/add_category' + ' -- ' + 'Parameter: UserId, Titel' + ' -- ' + 'Response: CategoryID';
    response += '<br>';
    response += '/get_all_categories' + ' -- ' + 'Parameter: UserId' + ' -- ' + 'Response: Alle offenen Kategorien des Users als JSON Objekt';

    res.send(response);
});

router.get('/add_category', async (req, res) => {
    const userid = req.query.userid;
    const title = req.query.title;
    const conn = await dbConnection();
    let response = '';

    if (!userid || !title) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }
    try {
        let data =  await add_category(conn, userid, title);
        let categoryid = data.insertId;
        response += `{"CategoryID":${categoryid}}`;
    } catch (e) {
        console.log(e);
    }
    res.send(response);
    conn.close();
});

router.get('/get_all_categories', async (req, res) => {
    const userid = req.query.userid;
    const conn = await dbConnection();
    let response = await get_all_categories(conn, userid);
    res.send(response);
    conn.close();
});



async function add_category(conn, userid, title) {
    try {
        const data = await conn.query("INSERT INTO `ToDo-Kategorie` (UserID, Kategoriename) VALUES(?,?)", [userid, title]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function get_all_categories(conn, userid) {
    try {
        const data = await conn.query("SELECT CategoryID, Kategoriename FROM `ToDo-Kategorie` WHERE UserID = ?", [userid]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

// export the router module so that server.js file can use it
module.exports = router;