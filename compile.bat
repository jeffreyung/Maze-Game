@echo off
title Build
echo compiling...
javac -d bin -sourcepath src src/comp2911/*.java
javac -d bin -sourcepath src src/comp2911/game/*.java
javac -d bin -sourcepath src src/comp2911/gui/*.java
pause