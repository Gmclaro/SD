#!/bin/bash
rm ./*/*.class

javac ./*/*.java

java main.GameOfRope

cat out
