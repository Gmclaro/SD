#!/bin/bash
export CLASSPATH=./commonInfra/genclass.jar

sleep 0.1

rm ./*/*.class

sleep 0.1

javac ./*/*.java

sleep 0.1

java main.GameOfRope

sleep 0.1

cat out
