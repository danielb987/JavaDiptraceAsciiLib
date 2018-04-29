<?php

$file = file_get_contents($argv[1]);

$pattern = "/\\<b\\>Total Warnings\\<\\/b\\>\s+\\<\\/td\\>\s+\\<td align\\=\\\"right\\\"\\>\s+\\<b\\>(\d+)\\<\\/b\\>/ims";

$result = preg_match($pattern, $file, $matches);

// Return an error code if num total warnings is not zero
if ($matches[1] != "0") {
	
	readfile("./build/findbugs/findbugs.txt");
	
	echo "\n\nFindbugs has ".$matches[1]." warnings\n\n\n";
	exit(1);
}

?>