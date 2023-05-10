<?php
$servername = "localhost";
$username = "root";
$password = "root";
$database = "company";

//create connection
$connection = new mysqli($servername, $username, $password, $database);

$SSN = "";
$Fname = "";
$Lname = "";
$DOB = "";
$address = "";

$errorMessage = "";
$successMessage = "";


if ( $_SERVER['REQUEST_METHOD'] == 'POST'){
    $SSN = $_POST["SSN"];
    $Fname = $_POST["Fname"];
    $Lname = $_POST["Lname"];
    $DOB = $_POST["DOB"];
    $address = $_POST["address"];
    do {
        if ((strlen($SSN) != 9)){
            $errorMessage = "SSN must be 9 digits";
            break;
        }
        if ( empty($SSN) ||empty($Fname) || empty($Lname) || empty($DOB)|| empty($address) ){
            $errorMessage = "All the fields are required";
            break;
        }

        // add new client to database
        $sql = "INSERT INTO employee (SSN,  Fname, Lname, DOB, address)" .
            "VALUES ('$SSN',  '$Fname', '$Lname', '$DOB', '$address')";
        $result = $connection->query($sql);
        
        if (!$result) {
            $errorMessage = "Invalid query: " . $connection->error;
            break;
        }

        $SSN = "";
        $Fname = "";
        $Lname = "";
        $DOB = "";
        $address = "";
        $successMessage = "Client added correctly";

        header("location: index1.php");
        exit;
        
    } while (false);

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
        <h2>New Employee</h2>

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
                <label class="col-sm-3 col-form-label">DOB</label>
                <div class="col-sm-6">
                    <input type="date" class="form-control" name="DOB" value="<?php echo $DOB; ?>">
                </div>
            </div>
            <div class="row mb-3">
                <label class="col-sm-3 col-form-label">Address</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" name="address" value="<?php echo $address; ?>">
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