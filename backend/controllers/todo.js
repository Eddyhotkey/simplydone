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
    response += '/get_all_open_todos' + ' -- ' + 'Parameter: UserId' + ' -- ' + 'Response: Alle offenen ToDos des Users als JSON Objekt';
    response += '<br>';
    response += '/all_open_todos' + ' -- ' + 'Parameter: UserId' + ' -- ' + 'Response: Alle offenen ToDos des Users leserlich';

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

    if (!userid || !title || !dueday || !category || !priority) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }
    try {
        let data =  await add_todo_to_database(conn, userid, category, title, description, dueday, priority);
        let todoid = data.insertId;
        response += `{"ToDoID":${todoid}}`;
    } catch (e) {
        console.log(e);
    }
    res.send(response);
    conn.close();
});

router.get('/get_all_open_todos', async (req, res) => {
    const userid = req.query.userid;
    const conn = await dbConnection();
    let response = await get_all_open_todos(conn, userid);
    res.send(response);
    conn.close();
});

router.get('/get_all_open_todos_today', async (req, res) => {
    const userid = req.query.userid;
    const conn = await dbConnection();
    let response = await get_all_open_todos_today(conn, userid);
    res.send(response);
    conn.close();
});

router.get('/get_all_open_other_todos', async (req, res) => {
    const userid = req.query.userid;
    const conn = await dbConnection();
    let response = await get_all_open_other_todos(conn, userid);
    res.send(response);
    conn.close();
});

router.get('/all_open_todos', async (req, res) => {
    const userid = req.query.userid;
    const conn = await dbConnection();
    var rows = await get_all_open_todos(conn, userid);
    let response = '';
    for (i = 0, len = rows.length; i < len; i++) {
        response += '<br>' + (`${rows[i].ToDoID} - ${rows[i].UserID} - ${rows[i].CategoryID} - ${rows[i].Titel} - ${rows[i].Beschreibung} - ${rows[i].Fälligkeitsdatum} - ${rows[i].Priorität}`);
    }
    response += '<br><br> Insgesamt ' + rows.length + ' ToDos';
    res.send(response);
    conn.close();
});

router.get('/update_todo', async (req, res) => {
    const category = req.query.category;
    const title = req.query.title;
    const description = req.query.description;
    const dueday = req.query.dueday;
    const priority = req.query.priority;
    const todoid = req.query.todoid;
    const conn = await dbConnection();
    let response = '';

    if (!todoid || !title || !dueday || !category || !priority) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }
    try {
       let data = await update_todo_in_database(conn, category, title, description, dueday, priority, todoid);
       response = `{"affectedRows":${data.affectedRows}}`;
    } catch (e) {
        console.log(e);
    }
    res.send(response);
    conn.close();
});
router.get('/close_todo', async (req, res) => {
    const todoid = req.query.todoid;
    const conn = await dbConnection();
    let response = '';

    if (!userid || !title || !dueday || !category || !priority) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }
    try {
        let data =  await close_todo_in_database(conn, todoid);
        let todoid = data.insertId;
        response += `{"ToDoID":${todoid}}`;
    } catch (e) {
        console.log(e);
    }
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

async function update_todo_in_database(conn, category, title, description, dueday, priority, todoid) {
    try {
        const data = await conn.query("UPDATE `ToDo-Eintrag` SET CategoryID = ?, Titel = ?, Beschreibung = ?, Fälligkeitsdatum = ?, Priorität = ? WHERE ToDoID = ?", ['1', title, description, dueday, priority, todoid]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function close_todo_in_database(conn, todoid) {
    try {
        const data = await conn.query("UPDATE `ToDo-Eintrag` SET Status = 'close' WHERE ToDoID = ?", [todoid]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function get_all_open_todos(conn, userid) {
    try {
        const data = await conn.query("SELECT ToDoID, UserID, CategoryID, Titel, Beschreibung, Fälligkeitsdatum, Priorität, Status FROM `ToDo-Eintrag` WHERE UserID = ? AND Status = ?", [userid, 'open']);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function get_all_open_todos_today(conn, userid) {
    try {
        const data = await conn.query("SELECT ToDoID, UserID, CategoryID, Titel, Beschreibung, Fälligkeitsdatum, Priorität, Status FROM `ToDo-Eintrag` WHERE UserID = ? AND Status = ? AND Fälligkeitsdatum = CURRENT_DATE", [userid, 'open']);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}
async function get_all_open_other_todos(conn, userid) {
    try {
        const data = await conn.query("SELECT ToDoID, UserID, CategoryID, Titel, Beschreibung, Fälligkeitsdatum, Priorität, Status FROM `ToDo-Eintrag` WHERE UserID = ? AND Status = ? AND Fälligkeitsdatum <> CURRENT_DATE", [userid, 'open']);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

// export the router module so that server.js file can use it
module.exports = router;