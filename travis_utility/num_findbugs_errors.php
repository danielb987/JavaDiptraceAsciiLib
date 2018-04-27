<?php

echo "Run PHP program. /Daniel.";

$file = readfile($argv[1]);

echo $file;

$pattern = "/\\<b\\>Total Warnings\\<\\/b\\>/ims";

$result = preg_match($pattern, $file, $matches);

print_r($matches);

echo "Result: $result\n";

?>