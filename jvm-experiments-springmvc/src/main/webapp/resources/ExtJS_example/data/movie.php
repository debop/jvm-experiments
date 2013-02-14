<?php

	$con = mysql_connect("localhost","id","pw");
		mysql_select_db('db name');
	$sql = "SELECT * FROM movies";
	$arr = array();

	header('Content-Type: application/json');
	If (!$rs = mysql_query($sql)) {
		Echo '{success:false, message: "'.mysql_error().'"}';
	}else{
		while($obj = mysql_fetch_object($rs)){
			$arr[] = $obj;
		}

		Echo '{success:true,rows:'.json_encode($arr).'}';
	}
	mysql_close($con);
?>
