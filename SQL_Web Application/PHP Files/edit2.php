<?php

$servername = "localhost";
$username = "root";
$password = "root";
$database = "company";

//create connection
$connection = new mysqli($servername, $username, $password, $database);

$id = "";
$SSN = "";
$Fname = "";
$Lname = "";
$relationship = "";

$errorMessage = "";
$successMessage = "";

if ( $_SERVER['REQUEST_METHOD'] == 'GET'){
    // GET method: show the data of client
    if ( !isset($_GET["id"])) {
        header("location: index.php");
        exit;
    }
    $id = $_GET["id"];

    // read the row of the selected client from database table
    $sql = "SELECT * FROM dependent WHERE id=$id";
    $result = $connection->query($sql);
    $row = $result->fetch_assoc();

    if (!$row) {
        header("location: index.php");
        exit;
    }

    $SSN = $row["SSN"];
    $Fname = $row["Fname"];
    $Lname = $row["Lname"];
    $relationship = $row["relationship"];
    
}
else {
    // POST method: Update the client
    $id = $_POST["id"];
    $SSN = $_POST["SSN"];
    $Fname = $_POST["Fname"];
    $Lname = $_POST["Lname"];
    $relationship = $_POST["relationship"];
    

    do {
        if ((strlen($SSN) != 9)){
            $errorMessage = "SSN must be 9 digits";
            break;
        }
       
        if ( empty($SSN) ||empty($Fname) || empty($Lname) || empty($relationship)){
            $errorMessage = "All the fields are required";
            break;
        }
        $sql = "UPDATE dependent 
               SET SSN = '$SSN', Fname = '$Fname', Lname = '$Lname', relationship = '$relationship' 
               WHERE id = $id";
        $result = $connection->query($sql);
        
        if (!$result) {
            $errorMessage = "Invalid query: " . $connection->error;
            break;
        }

        $successMessage = "Client added correctly";

        header("location: index1.php");
        exit;
    
    } while (true);
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Shop</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container my-5">
        <h2>Update Relationship</h2>

        <?php
        if ( !empty($errorMessage)){
            echo "
            <div class='alter alter-warning alter-dimissible fade show' role='alter'>
                <strong>$errorMessage</strong>
                <button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>
            </div>
            ";
        }
        ?>

        <form method="post">
            <input type ="hidden" name="id" value="<?php echo $id; ?>">
            <div class="row mb-3">
                <label class="col-sm-3 col-form-label">SSN</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="SSN" value="<?php echo $SSN; ?>">
                </div>
            </div>
            
            <div class="row mb-3">
                <label class="col-sm-3 col-form-label">Fname</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="Fname" value="<?php echo $Fname; ?>">
                </div>
            </div>
            
            <div class="row mb-3">
                <label class="col-sm-3 col-form-label">Lname</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="Lname" value="<?php echo $Lname; ?>">
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-3 col-form-label">Relationship</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="relationship" value="<?php echo $relationship; ?>">
                </div>
            </div>
            

            <?php
            if ( !empty($successMessage)){
                echo "
                <div class='row mb-3'>
                    <div class='offset-sm-3 col-sm-6'>
                        <div class='alter alter-warning alter-dimissible fade show' role='alter'>
                            <strong>$successMessage</strong>
                            <button type='button' class='btn-close' data-bs-dismiss='alert' aria-label='Close'></button>
                        </div>
                    </div>        
                </div>
                ";
            }    

            ?>


            <div class="row mb-3">
                <div class="offset-sm-3 col-sm-3 d-grid">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
                <div class="col-sm-3 d-grid">
                    <a class="btn btn-outline-primary" href="index1.php" role="button">Cancel</a>
                </div>
            </div>
               
        </form>
    </div>
    
</body>
</html>