<?php

	$con = mysql_connect("localhost","id","pw");
	mysql_select_db('db name');

	$action  = ($_REQUEST['action'] != '') ? $_REQUEST['action'] : 'update';

	$arr = array();
	Switch ($action){
		Case 'create':
			$sql = "INSERT INTO movies (title,director,genre,tagline) VALUES ('".$_REQUEST['title']."','',0,'')";
		Break;
		Case 'update':
			$sql = "UPDATE movies SET ".$_REQUEST['field']." = '".$_REQUEST['value']."' WHERE id = ".$_REQUEST['id'];
		Break;
		Case 'delete':
			$sql = "DELETE FROM movies WHERE id = ".$_REQUEST['id'];
		Break;
	}

	header('Content-Type: application/json');

	If (!$rs = mysql_query($sql)) {
		Echo '{success:false,message:"'.mysql_error().'"}';
	}else{
		Switch ($action){
			Case 'create':
				Echo '{success:true,insert_id:'.mysql_insert_id().'}';
			Break;
			Case 'update':
				Echo '{success:true}';
			Break;
			Case 'delete':
				Echo '{success:true}';
			Break;
		}
	}
	mysql_close($con);
?>