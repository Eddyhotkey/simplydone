<?php

$servername = "localhost";
$username = "d03f8fb1";
$password = "zicpok-fecGym-renby0";
$dbname = "d03f8fb1";

$conn = new mysqli($servername, $username, $password, $dbname);


if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

?>