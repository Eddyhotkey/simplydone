<?php

$servername = "localhost";
$username = "d03f8fb1";
$password = "zicpok-fecGym-renby0";
$dbname = "d03f8fb1";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Datenbankabfrage
$sql = "SELECT * FROM Benutzer";
$result = $conn->query($sql);

$data = array();

// Wenn es Ergebnisse gibt, ein Datenfeld bilden
if ($result->num_rows > 0) {
  while($row = $result->fetch_assoc()) {
    $data[] = $row;
  }
}

// Schließen der Datenbankverbindung
$conn->close();

// Kodierung der Daten in das JSON-Format
$json_data = json_encode($data);

// Speichern von Daten in einer Datei
$file = 'data.json';
file_put_contents($file, $json_data);

echo "Die JSON-Datei wurde erfolgreich erstellt.";

?>
