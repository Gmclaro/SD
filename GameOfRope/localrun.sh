#!/bin/bash
export CLASSPATH=$CLASSPATH:$PWD/commonInfra/genclass.jar

read -p "rm .class? (y/n)" answer

if [ "$answer" = "y" ] || [ -z "$answer" ]; then
    rm ./*/*/*.class
    rm ./commonInfra/*.class
    rm log
fi

read -p "compile? (y/n)" answer
if [ "$answer" = "n" ]; then
    exit 0
fi

javac -cp ".:./genclass.jar" ./*/*/*.java



