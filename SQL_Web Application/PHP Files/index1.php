<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Shop</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css">
    
</head>
<body>
    <div class="container my-5">
        <h2>List of Employees</h2>
        <a class="btn btn-primary" href="create.php" role="button">New Employee</a>
        <br>
        <table class="table">
            <thead>
                <tr>
                    
                    <th>SSN</th>
                    <th>FName</th>
                    
                    <th>Lname</th>
                    <th>DOB</th>
                    <th>Address</th>
                    <th>Action</th>

                </tr>
            </thead>
            <tbody>
                <?php
                $servername = "localhost";
                $username ="root";
                $password = "root";
                $database = "company";

                //create connection
                $connection = new mysqli($servername, $username, $password, $database);
                if ($connection->connect_error){
                    die("Connection failed: ".$connection->connect_error);
                }

                //read all row from database table
                $sql = "SELECT * FROM employee";
                $result = $connection->query($sql);

                if (!$result){
                    die("Invalid query: ".$connection->error);
                }

                //read data of each row
                while($row = $result->fetch_assoc()){
                  echo "
                <tr>
                    
                    <td>$row[SSN]</td>
                    <td>$row[Fname]</td>
                    
                    <td>$row[Lname]</td>
                    <td>+$row[DOB]</td>
                    <td>$row[address]</td>
                    
                    <td>
                        <a class'btn btn-primary btn-sm' href='edit.php?id=$row[id]'>Edit</a>
                        <a class='btn btn-primary btn-sm' href='delete.php?id=$row[id]'>Delete</a>
                    </td>
                </tr>
                  ";   
                }
                ?>
                
            </tbody>
        </table>    

    </div>
    <div class="container my-5">
        <h2>List of Relationships</h2>
        <a class="btn btn-primary" href="create2.php" role="button">New Relationahip</a>
        <br>
        <table class="table">
            <thead>
                <tr>
                    
                    <th>SSN</th>
                    <th>FName</th>
                    
                    <th>Lname</th>
                    
                    <th>Relationship</th>
                    <th>Action</th>

                </tr>
            </thead>
            <tbody>
                <?php
                $servername = "localhost";
                $username ="root";
                $password = "root";
                $database = "company";

                //create connection
                $connection = new mysqli($servername, $username, $password, $database);
                if ($connection->connect_error){
                    die("Connection failed: ".$connection->connect_error);
                }

                //read all row from database table
                $sql = "SELECT * FROM dependent";
                $result = $connection->query($sql);

                if (!$result){
                    die("Invalid query: ".$connection->error);
                }

                //read data of each row
                while($row = $result->fetch_assoc()){
                  echo "
                <tr>
                    
                    <td>$row[SSN]</td>
                    <td>$row[Fname]</td>
                    
                    <td>$row[Lname]</td>
                    
                    <td>$row[relationship]</td>
                    
                    <td>
                        <a class'btn btn-primary btn-sm' href='edit2.php?id=$row[id]'>Edit</a>
                        <a class='btn btn-primary btn-sm' href='delete2.php?id=$row[id]'>Delete</a>
                    </td>
                </tr>
                  ";   
                }
                ?>
                
            </tbody>
        </table>    

    </div>
    
</body>
</html>
