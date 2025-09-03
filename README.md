# ⚡ Lightweight Java Compiler

A lightweight Java compiler that parses, analyzes, and executes Java code with a Maven-based build system.

## 🚀 Features
- Parse and analyze Java source code
- Basic syntax checking
- Execute main classes
- Maven-based project structure

## 🛠️ Tech Stack
- **Language:** Java
- **Build Tool:** Maven

## ⚙️ Installation & Usage (Windows)
```cmd
:: Navigate to Maven bin folder (optional)
cd C:\apache-maven-3.9.11-bin\apache-maven-3.9.11\bin

:: Verify PATH environment variable
echo %PATH%

:: Check Maven version
mvn --version

:: Navigate to project folder
cd C:\Users\projects\Lightweight-Java-Compiler

:: Build the project using Maven
mvn clean install

:: Alternative: run Maven using full path (if PATH not set)
"C:\apache-maven-3.9.11-bin\apache-maven-3.9.11\bin\mvn.cmd" clean install

:: Run the compiler
mvn exec:java -Dexec.mainClass="compiler.Main"
