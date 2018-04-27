<?php

echo "Run PHP program. /Daniel.";

$file = file_get_contents($argv[1]);

echo $file;

// $pattern = "/\\<b\\>Total Warnings\\<\\/b\\>/ims";
$pattern = "/\\<b\\>Total Warnings\\<\\/b\\>\s+\\<\\/td\\>\s+\\<td align\\=\\\"right\\\"\\>\s+\\<b\\>(\d+)\\<\\/b\\>/ims";

$result = preg_match($pattern, $file, $matches);

// print_r($matches);

// echo "Result: $result\n";

// Return an error code if num total warnings is not zero
if ($matches[1] != "0") {
	echo "\n\nFindbugs has ".$matches[1]." warnings\n\n\n";
	exit(1);
}

?>