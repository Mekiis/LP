<?php
	$textFile = "LEDcolor.txt";
	
	if(isset($_GET['colorPicker'])){
	
		$ch = curl_init('http://www.earthtools.org/timezone-1.1/48.853/2.35');
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		$page = curl_exec($ch);
		curl_close($ch);
		
		$xml = new SimpleXMLElement($page);
		echo($xml[0]->localtime);
		
		$input_line = $xml[0]->localtime;
  		preg_match("/([0-9]{2}):([0-9]{2}):([0-9]{2})/", $input_line, $output_array);
  		$hours = $output_array[1];
  		$minuts = $output_array[2];
  		$seconds = $output_array[3];
	
		$rgb = hexdec($_GET['colorPicker']);
		
		$red = ($rgb>>16)&0x0ff;
		$green =($rgb>>8) &0x0ff;
		$blue = ($rgb)    &0x0ff;
		
		echo($rgb."/".$red."/".$green."/".$blue);
	
		$fp = fopen($textFile, 'w') or die('Something went wrong !');
		fwrite($fp, $rgb);
		fclose($fp);
	}
	
	header("Location : index.html");
?>
