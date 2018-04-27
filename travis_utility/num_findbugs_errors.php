<?php

echo "Run PHP program. /Daniel.";

$file = file_get_contents($argv[1]);

echo $file;

$pattern = "/\\<b\\>Total Warnings\\<\\/b\\>/ims";
$pattern = "/Total Warnings/ims";

$result = preg_match($pattern, $file, $matches);

print_r($matches);

echo "Result: $result\n";

?>