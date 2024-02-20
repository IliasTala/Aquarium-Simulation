#!/bin/bash 

javac *.java
java HELBAquarium
find . -type f -name "*.class" -delete
