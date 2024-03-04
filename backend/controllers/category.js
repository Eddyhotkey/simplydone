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

router.get('/create_sharing', async (req, res) => {
    const userid = req.query.userid;
    const categoryid = req.query.categoryid;
    const recieverid = req.query.recieverid;
    const conn = await dbConnection();
    let response = '';

    let data = await create_sharing(conn, categoryid, userid, recieverid);
    let sharingID = data.insertId;
    response += `{"SharingID":${sharingID}}`;
    res.send(response);
    conn.close();
});

router.get('/delete_category', async (req, res) => {
    const categoryid = req.query.categoryid;
    const conn = await dbConnection();
    let response = '';

    if (!categoryid) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }
    try {
        let data =  await delete_category_in_database(conn, categoryid);
        response = `{"affectedRows":${data.affectedRows}}`;
    } catch (e) {
        console.log(e);
    }
    res.send(response);
    conn.close();
});

router.get('/delete_sharing', async (req, res) => {
    const sharingid = req.query.sharingid;
    const conn = await dbConnection();
    let response = '';

    if (!sharingid) {
        return res.status(400).json({ error: 'Parameter fehlt in der Anfrage.' });
    }
    try {
        let data =  await delete_sharing_in_database(conn, sharingid);
        response = `{"affectedRows":${data.affectedRows}}`;
    } catch (e) {
        console.log(e);
    }
    res.send(response);
    conn.close();
});

router.get('/get_shared_users_for_category', async (req, res) => {
    const userid = req.query.userid;
    const categoryid = req.query.categoryid;
    const conn = await dbConnection();
    let response = await get_shared_users_for_category(conn, userid, categoryid);
    res.send(response);
    conn.close();
});

router.get('/get_category_name', async (req, res) => {
    const categoryid = req.query.categoryid;
    const conn = await dbConnection();
    let data = await get_category_name(conn, categoryid);
    let response = data[0];
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

async function create_sharing(conn, categoryString, userString, recieverString) {
    try {
        let categoryid = parseInt(categoryString);
        let userid = parseInt(userString);
        let recieverid = parseInt(recieverString);

        const data = await conn.query("INSERT INTO `Benutzer-Sharing` (CategoryID, UserID, EmpfängerUserID) VALUES(?,?,?)", [categoryid, userid, recieverid]);
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

async function delete_category_in_database(conn, categoryid) {
    try {
        const data = await conn.query("DELETE FROM `ToDo-Kategorie` WHERE CategoryID = ?", [parseInt(categoryid)]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function delete_sharing_in_database(conn, sharingID) {
    try {
        const data = await conn.query("DELETE FROM `Benutzer-Sharing` WHERE SharingID = ?", [sharingID]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function get_shared_users_for_category(conn, userid, categoryid) {
    try {
        const data = await conn.query("SELECT SharingID, CategoryID, EmpfängerUserID FROM `Benutzer-Sharing` WHERE UserID = ? AND CategoryID = ?", [userid, categoryid]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

async function get_category_name(conn, categoryid) {
    try {
        const data = await conn.query("SELECT Kategoriename FROM `ToDo-Kategorie` WHERE CategoryID = ?", [categoryid]);
        return data;
    } catch (error) {
        console.error('Error querying the database:', error);
        throw error;
    }
}

// export the router module so that server.js file can use it
module.exports = router;