<?php
$servername = "localhost";
$username = "d03f8fb1";
$password = "zicpok-fecGym-renby0";
$dbname = "d03f8fb1";


$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM `ToDo-Eintrag`";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
  echo "<table>";
  echo "<tr><th>ToDoID</th><th>CategoryID</th><th>UserID</th><th>Titel</th><th>Beschreibung</th><th>Erstelldatum</th><th>Fälligkeitsdatum</th><th>Priorität</th><th>Status</th></tr>";
  while($row = $result->fetch_assoc()) {
    echo "<tr>";
    echo "<td>" . $row["ToDoID"] . "</td>";
    echo "<td>" . $row["CategoryID"] . "</td>";
    echo "<td>" . $row["UserID"] . "</td>";
    echo "<td>" . $row["Titel"] . "</td>";
    echo "<td>" . $row["Beschreibung"] . "</td>";
    echo "<td>" . $row["Erstelldatum"] . "</td>";
    echo "<td>" . print_r($row) . "</td>";
    echo "<td>" . $row["Priorität"] . "</td>";
    echo "<td>" . $row["Status"] . "</td>";
    echo "</tr>";
  }
  echo "</table>";
} else {
  echo "Keine Daten";
}
$conn->close();
?>
