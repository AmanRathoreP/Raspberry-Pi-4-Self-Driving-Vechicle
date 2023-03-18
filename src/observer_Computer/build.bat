@echo off

python build\generator.py . java files_info.txt

javac -d build\classes -cp build\resources\*.jar @files_info.txt
@echo all classes created successfully

cd build\classes
@echo switched to the classes directory in build directory

python ..\generator.py . class classes_info.txt
jar cfve ..\app.jar app @classes_info.txt
@echo Jar files created successfully

@echo running the app
java -jar ..\app.jar

cd ..\..
endlocal
