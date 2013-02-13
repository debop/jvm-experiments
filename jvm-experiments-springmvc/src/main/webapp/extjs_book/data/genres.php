<?php
	$con = mysql_connect("localhost","id","pw");
	mysql_select_db('db name');

	// connection to database goes here
	$result = mysql_query('SELECT id, genre_name FROM genre');
	If (mysql_num_rows($result) > 0) {
		while ($obj = mysql_fetch_object($result)) {
			$arr[] = $obj;
		}
	}
	Echo '{rows:'.json_encode($arr).'}';

	mysql_close($con);
?>