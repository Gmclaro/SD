#!/bin/bash
export CLASSPATH=./commonInfra/genclass.jar

rm ./*/*.class

javac ./*/*.java

java main.GameOfRope

cat out
