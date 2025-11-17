@echo off

if not exist bin mkdir bin

if "%1"=="test" goto test
if "%1"=="tests" goto test

REM Compilar e executar o sistema
javac -encoding UTF-8 -d bin src/main/java/main/Main.java src/main/java/main/model/*.java src/main/java/main/model/enums/*.java src/main/java/main/controller/*.java src/main/java/main/view/*.java src/main/java/main/util/*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha na compilacao!
    pause
    exit /b 1
)

echo [OK] Compilacao concluida!
echo.

java -cp bin main.Main %*
pause
exit /b 0

:test
REM Compilar e executar os testes
javac -encoding UTF-8 -d bin src/main/java/main/model/*.java src/main/java/main/model/enums/*.java src/main/java/main/util/*.java src/main/java/main/controller/*.java src/test/java/test/util/*.java src/test/java/test/controller/*.java src/test/java/test/view/*.java src/test/java/test/TestRunner.java

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha na compilacao dos testes!
    pause
    exit /b 1
)

echo [OK] Compilacao dos testes concluida!
echo.

java -ea -cp bin test.TestRunner
pause
exit /b 0