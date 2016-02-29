<?php
require "connect.php";
$useid=$_POST["useid"];
$username=$_POST["username"];
$current_location=$_POST["current_location"];

$sql_query="insert into locations values('$useid','$username','$current_location');";

if(mysqli_query($con,$sql_query))
{
	echo"Data insertion Success";
}
else
{
	echo "Data error".mysqli_error($con); 
}
?>