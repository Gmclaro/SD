#!/bin/bash
export CLASSPATH=$CLASSPATH:$PWD/commonInfra/genclass.jar

read -p "rm .class? (y/n)" answer

if [ "$answer" = "y" ] || [ -z "$answer" ]; then
    rm -f ./*/*.class
    rm log
fi

read -p "compile? (y/n)" answer
if [ "$answer" = "n" ]; then
    exit 0
fi

javac ./*/*.java

echo "java main.GameOfRope"

java main.GameOfRope

if [ $? -eq 0 ]; then
    sleep 0.1
    bat log
fi
