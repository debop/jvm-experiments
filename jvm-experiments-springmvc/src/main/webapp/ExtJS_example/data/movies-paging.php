<?php
	$con = mysql_connect("localhost","id","pw");
	mysql_select_db('db name');

	$start = ($_REQUEST['start'] != '') ? $_REQUEST['start'] : 0;
	$limit = ($_REQUEST['limit'] != '') ? $_REQUEST['limit'] : 3;
	$count_sql = "SELECT * FROM movies";
	$sql = $count_sql . " LIMIT ".$start.", ".$limit;
	$arr = array();
	If (!$rs = mysql_query($sql)) {
		Echo "{success:false}";
	}else{
		$rs_count = mysql_query($count_sql);
		$results = mysql_num_rows($rs_count);
		while($obj = mysql_fetch_object($rs)){
			$arr[] = $obj;
		}
		Echo "{success:true,results:".$results.",
		rows:".json_encode($arr)."}";
	}
	mysql_close($con);
?>