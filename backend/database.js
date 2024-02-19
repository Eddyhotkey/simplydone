const mariadb = require("mariadb");

async function dbConnection() {
   let pool;

   try {
      pool = await mariadb.createConnection({
         host: "w01bd6fb.kasserver.com",
         user: "d03f8fb1",
         password: "zicpok-fecGym-renby0",
         database: "d03f8fb1",
         connectionLimit: 50,
      });
      return pool;
   } catch (err) {
      // Manage Errors
      console.log(err);
   }
}

module.exports = dbConnection;