<?php

echo "Run PHP program. /Daniel.";

$file = readfile($argv[1]);

echo $file;

$pattern = "/\\<b\\>Total Warnings\\<\\/b\\>/ims";
$pattern = "/Total Warnings/ims";
$pattern = "/Total/";

$result = preg_match($pattern, $file, $matches);

print_r($matches);

echo "Result: $result\n";

preg_match('/(foo)(bar)(baz)/', 'foobarbaz', $matches, PREG_OFFSET_CAPTURE);
print_r($matches);

?>