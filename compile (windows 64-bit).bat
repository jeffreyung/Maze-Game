@echo off
title Build
echo compiling...
"C:/Program Files (x86)/Java/jdk1.8.0_111/bin/javac" -d bin -sourcepath src src/comp2911/*.java
"C:/Program Files (x86)/Java/jdk1.8.0_111/bin/javac" -d bin -sourcepath src src/comp2911/game/*.java
"C:/Program Files (x86)/Java/jdk1.8.0_111/bin/javac" -d bin -sourcepath src src/comp2911/gui/*.java
pause