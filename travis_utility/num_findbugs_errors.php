<?php

echo "Run PHP program. /Daniel.";

$file = readfile($argv[1]);

echo $file;

echo "\n-------------------------------\n";

$pattern = "/\\<b\\>Total Warnings\\<\\/b\\>/ims";
$pattern = "/Total Warnings/ims";
$pattern = "/Total/";
$pattern = '/Total/s';

echo "-------------------------------\n";

$result = preg_match($pattern, $file, $matches);

echo "-------------------------------\n";

print_r($matches);

echo "-------------------------------\n";

echo "Result: $result\n";

echo "-------------------------------\n";

preg_match('/(foo)(bar)(baz)/', 'foobarbaz', $matches, PREG_OFFSET_CAPTURE);
print_r($matches);

?>