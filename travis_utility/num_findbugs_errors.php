<?php

echo "Run PHP program. /Daniel.";

$file = readfile($argv[1]);

echo $file;

echo "-------------------------------";

$pattern = "/\\<b\\>Total Warnings\\<\\/b\\>/ims";
$pattern = "/Total Warnings/ims";
$pattern = "/Total/";
$pattern = '/Total/s';

echo "-------------------------------";

$result = preg_match($pattern, $file, $matches);

echo "-------------------------------";

print_r($matches);

echo "-------------------------------";

echo "Result: $result\n";

echo "-------------------------------";

preg_match('/(foo)(bar)(baz)/', 'foobarbaz', $matches, PREG_OFFSET_CAPTURE);
print_r($matches);

?>