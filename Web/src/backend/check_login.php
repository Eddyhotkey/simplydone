<?php
// Verbindung zur Datenbank herstellen
$servername = "localhost";
$username = "d03f8fb1";
$password = "zicpok-fecGym-renby0";
$dbname = "d03f8fb1";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$username = $_POST['username'];
$password = $_POST['password'];

// Vorbereitete Abfrage zur Vermeidung von SQL-Injection
$stmt = $conn->prepare("SELECT UserID FROM Benutzer WHERE Username=? AND Passwort=?");
$stmt->bind_param("ss", $username, $password);
$stmt->execute();
$result = $stmt->get_result();

// Prüfen, ob es einen Benutzer mit den angegebenen Anmeldedaten gibt
if ($result->num_rows > 0) {
  // Benutzer-ID abrufen
  $row = $result->fetch_assoc();
  $userID = $row['UserID'];
  // Rückgabe der Benutzer-ID als Antwort auf die Anfrage
  echo $userID;
} else {
  // Wenn der Benutzer nicht existiert, wird ein leerer String zurückgegeben.
  echo "";
}

$stmt->close();
$conn->close();
?>
