<?php
if ( isset($_GET["id"])){
    $id = $_GET["id"];

    $servername = "localhost";
    $username = "root";
    $password = "root";
    $database = "company";

    //create connection
    $connection = new mysqli($servername, $username, $password, $database);

    $sql = "DELETE FROM employee WHERE id=$id";
    $connection->query($sql);

}

header("location:index1.php");
exit;

?>