// routes/users.js
const express = require('express');
const dbConnection = require('../database');
const router = express.Router();

// Define a route
router.get('/', async (req, res) => {
    let response = '';
    response += 'Verfügbare Routen: <br><br>';

    response += '/add_user' + ' -- ' + 'Parameter: Username, Passwort, Vorname, Nachname, Email' + ' -- ' + 'Response: leer';
    response += '<br>';
    response += '/check-credential' + ' -- ' + 'Parameter: Username' + ' -- ' + 'Response: Passwort';
    response += '<br>';
    response += '/all_usersl' + ' -- ' + 'Parameter: -' + ' -- ' + 'Response: Alle User';

    res.send(response);
});

router.get('/add_user', async (req, res) => {
    const username = req.query.username;
    const password = req.query.password;
    const forename = req.query.forename;
    const lastname = req.query.lastname;
    const email = req.query.email;
    const conn = await dbConnection();
    let response = '';

    if (!username || !password || !forename || !lastname ||!email) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }

    let userResult = await check_user_availability(conn, username);
    console.log(userResult[0])
    if(userResult[0] === undefined) {
        try {
            await add_user_to_database(conn, username, password, forename, lastname, email);
            response += 'User hinzugefügt';
        } catch (e) {
            console.log(e);
        }
    } else {
        if(userResult[0].Username === username) {
            return res.status(400).json({error: 'User bereits vorhanden.'});
        } else {
            return res.status(404).json({error: 'Fehler in der Datenbankabfrage.'});
        }
    }

    res.send(response);
    conn.close();
});

router.get('/check-credential', async (req, res) => {
    const username = req.query.username;
    const conn = await dbConnection();

    if (!username) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }
    let response = await get_password_by_username(conn, username);
    res.send(response[0]);
    conn.close();
});

router.get('/all_users', async (req, res) => {
    const conn = await dbConnection();
    var rows = await get_all_user(conn);
    let response = '';

    for (i = 0, len = rows.length; i < len; i++) {
        response += '<br>' + (`${rows[i].UserID} - ${rows[i].Username} - ${rows[i].Passwort} - ${rows[i].Vorname} - ${rows[i].Nachname} - ${rows[i].Email}`);
    }
    response += '<br><br> Insgesamt ' + rows.length + ' User';
    res.send(response);
    conn.close();
});

async function get_all_user(conn) {
    try {
        const rows = await conn.query("SELECT * FROM Benutzer");
        return rows;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function check_user_availability(conn, username) {
    try {
        const data = await conn.query("SELECT Username FROM Benutzer WHERE Username = ?", [username]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}
async function get_password_by_username(conn, username) {
    try {
        const data = await conn.query("SELECT Passwort FROM Benutzer WHERE Username = ?", [username]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}
async function add_user_to_database(conn, username, password, forename, lastname, email) {
    try {
        const data = await conn.query("INSERT INTO Benutzer (Username, Passwort, Vorname, Nachname, Email) VALUES(?,?,?,?,?)", [username, password, forename, lastname, email]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

// export the router module so that server.js file can use it
module.exports = router;