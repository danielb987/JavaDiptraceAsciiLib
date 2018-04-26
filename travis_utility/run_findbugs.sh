#!/bin/bash

# Abort on error
set -e

echo -e "Generate Findbugs report"

export FINDBUGS_HOME=./findbugs/findbugs-3.0.1

mkdir ./build/findbugs

java -jar $FINDBUGS_HOME/lib/findbugs.jar -textui -html -output ./build/findbugs/findbugs.html -sourcepath ./src ./build/classes ./lib
