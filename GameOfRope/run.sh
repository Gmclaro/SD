#!/bin/bash
export CLASSPATH=$CLASSPATH:$PWD/commonInfra/genclass.jar

rm ./*/*.class

javac ./*/*.java