@echo off

python build\generator.py . java files_info.txt

javac -d build\classes -cp ".\build\resources\jcommon-1.0.16.jar;.\build\resources\jfreechart-1.5.4.jar" @files_info.txt
@echo all classes created successfully

cd build\classes
@echo switched to the classes directory in build directory

python ..\generator.py . class classes_info.txt
java -cp ".;..\resources\jfreechart-1.5.4.jar;..\resources\jcommon-1.0.16.jar" app @classes_info.txt
@REM jar -cp ".;..\\resources\\jfreechart-1.5.4;..\\resources\\jcommon-1.0.0.jar" cfve ..\app.jar app @classes_info.txt
@REM @echo Jar files created successfully

@REM @echo running the app
@REM java -jar ..\app.jar

cd ..\..
endlocal
