<?php
 require "connect.php";
 $sql_query ="select * from locations;";
 $result=mysqli_query($con,$sql_query);
 
 if(mysqli_num_rows($result)>0)
 {
  $row=mysqli_fetch_assoc($result);
 $response= array();
 while($row = mysqli_fetch_array($result)){
 	array_push($response,array("useid"=>$row[0],"username"=>$row[1],"current_location"=>$row[2]));
 	$output[]=$row;

}
echo json_encode($output);
}
else
{
	echo "No info is available...";
}
?>