@echo off

if not exist bin mkdir bin

if "%1"=="test" goto test
if "%1"=="tests" goto test

REM Compilar e executar o sistema
javac -encoding UTF-8 -d bin src/main/Main.java src/main/model/*.java src/main/model/enums/*.java src/main/controller/*.java src/main/view/*.java src/main/util/*.java

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
javac -encoding UTF-8 -d bin src/main/model/*.java src/main/model/enums/*.java src/main/util/*.java src/main/controller/*.java src/test/util/*.java src/test/controller/*.java src/test/view/*.java src/test/TestRunner.java

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