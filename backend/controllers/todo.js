// routes/users.js
const express = require('express');
const dbConnection = require('../database');
const router = express.Router();

// Define a route
router.get('/', async (req, res) => {
    let response = '';
    response += 'Verfügbare Routen: <br><br>';

    response += '/add_todo' + ' -- ' + 'Parameter: String title, String description, LocalDate dueday, String kategory, String priority' + ' -- ' + 'Response: leer';
    response += '<br>';
    //response += '/check-credential' + ' -- ' + 'Parameter: Username' + ' -- ' + 'Response: Passwort';
    //response += '<br>';
    //response += '/all_usersl' + ' -- ' + 'Parameter: -' + ' -- ' + 'Response: Alle User';

    res.send(response);
});

router.get('/add_todo', async (req, res) => {
    const userid = req.query.userid;
    const title = req.query.title;
    const description = req.query.description;
    const dueday = req.query.dueday;
    const category = req.query.category;
    const priority = req.query.priority;
    const conn = await dbConnection();
    let response = '';

    if (!userid || !title || !description || !dueday || !category ||!priority) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }

    try {
        await add_todo_to_database(conn,userid, title, description, dueday, category, priority);
        response += 'ToDo hinzugefügt';
    } catch (e) {
        console.log(e);
    }


    res.send(response);
    conn.close();
});

router.get('/all_todos', async (req, res) => {
    const userid = req.query.userid;
    const conn = await dbConnection();
    var rows = await get_all_todos(conn, userid);
    let response = '';

    for (i = 0, len = rows.length; i < len; i++) {
        response += '<br>' + (`${rows[i].UserID} - ${rows[i].Username} - ${rows[i].Passwort} - ${rows[i].Vorname} - ${rows[i].Nachname} - ${rows[i].Email}`);
    }
    response += '<br><br> Insgesamt ' + rows.length + ' User';
    res.send(response);
    conn.close();
});


async function add_todo_to_database(conn, userid, category, title, description, dueday, priority) {
    try {
        const data = await conn.query("INSERT INTO `ToDo-Eintrag` (UserID, CategoryID, Titel, Beschreibung, Fälligkeitsdatum, Priorität, Status) VALUES(?,?,?,?,?,?,?)", [userid, '1', title, description, dueday, priority, "open"]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function get_all_todos(conn, userid) {
    try {
        const data = await conn.query("SELECT * FROM `ToDo-Eintrag` WHERE UserID = ?", [userid]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

// export the router module so that server.js file can use it
module.exports = router;