# Build
This directory contains some information about how to create the build for our app or how to run the application.

## Creating build
Follow the below given steps in order to create your own build for the app using _maven_

1. Navigate to the __root directory of the app__ i.e. where you can see `pom.xml` file.

2. Now run below command for creating a runnable jar file containing all the dependencies in it.
```bash
mvn clean compile assembly:single
```
3. Now run your script using the following command to run the jar file
```bash
java -jar target\RealTime_Analytical_Tool-1.0-SNAPSHOT-jar-with-dependencies.jar
```
## Running app
In order to just run the app without creating any build you can use the following command in the command prompt
```bash
mvn exec:java -Dexec.mainClass="observer_java_GUI.app"
```