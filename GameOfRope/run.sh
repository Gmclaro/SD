#!/bin/bash
export CLASSPATH=$CLASSPATH:$PWD/commonInfra/genclass.jar
NUMBER_OF_MATCH=1

if [ ! -z "$1" ]; then
    NUMBER_OF_MATCH=$1
fi


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

for ((i = 0; i < $NUMBER_OF_MATCH; i++)); do
     echo -e "\033[34mNEW RUN\033[0m"
    java main.GameOfRope
    sleep 0.1
done


#java main.GameOfRope

if [ $? -eq 0 ]; then
    sleep 0.1
    bat log
fi
