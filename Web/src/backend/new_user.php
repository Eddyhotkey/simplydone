<?php
// Verbindung zur Datenbank herstellen
$servername = "localhost";
$username = "d03f8fb1";
$password = "zicpok-fecGym-renby0";
$dbname = "d03f8fb1";

// Erstellen einer Verbindung
$conn = new mysqli($servername, $username, $password, $dbname);

// Überprüfen Sie die Verbindung
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Abrufen von Daten aus einer POST-Anfrage
$newLogin = $_POST['newLogin'];
$newMail = $_POST['newMail'];
$newUsername = $_POST['newUsername'];
$newUserSurname = $_POST['newUserSurname'];
$newPassword = $_POST['newPassword'];

// Prüfen, ob ein Benutzer mit demselben Login oder derselben E-Mail existiert
$checkUserQuery = "SELECT * FROM Benutzer WHERE Username='$newLogin' OR Email='$newMail'";
$result = $conn->query($checkUserQuery);

if ($result->num_rows > 0) {
  // Benutzer mit diesem Login oder dieser E-Mail existiert bereits
  echo "Error: // Benutzer mit diesem Login oder dieser E-Mail existiert bereits";
} else {
  // Хеширование пароля
  $hashedPassword = password_hash($newPassword, PASSWORD_DEFAULT);

  // SQL-Abfrage zum Einfügen von Daten in die Tabelle
  $sql = "INSERT INTO Benutzer (Username, Vorname, Nachname, Email, Passwort)
  VALUES ('$newLogin', '$newUsername', '$newUserSurname', '$newMail', '$newPassword')";

  if ($conn->query($sql) === TRUE) {
    // Weiterleitung zur Startseite
    header("Location: ../../index.html");
    exit; // Es ist wichtig, die Skriptausführung nach der Umleitung zu beenden
  } else {
    echo "Error: " . $sql . "<br>" . $conn->error;
  }
}

$conn->close();
?>
