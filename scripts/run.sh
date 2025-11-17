#!/bin/bash

if [ ! -d "bin" ]; then
    mkdir bin
fi

if [ "$1" = "test" ] || [ "$1" = "tests" ]; then
    # Compilar e executar os testes
    javac -encoding UTF-8 -d bin src/main/java/main/model/*.java src/main/java/main/model/enums/*.java src/main/java/main/util/*.java src/main/java/main/controller/*.java src/test/java/test/util/*.java src/test/java/test/controller/*.java src/test/java/test/view/*.java src/test/java/test/TestRunner.java

    if [ $? -ne 0 ]; then
        echo ""
        echo "[ERRO] Falha na compilacao dos testes!"
        exit 1
    fi

    echo "[OK] Compilacao dos testes concluida!"
    echo ""

    java -ea -cp bin test.TestRunner
    exit $?
else
    # Compilar e executar o sistema
    javac -encoding UTF-8 -d bin src/main/java/main/Main.java src/main/java/main/model/*.java src/main/java/main/model/enums/*.java src/main/java/main/controller/*.java src/main/java/main/view/*.java src/main/java/main/util/*.java

    if [ $? -ne 0 ]; then
        echo ""
        echo "[ERRO] Falha na compilacao!"
        exit 1
    fi

    echo "[OK] Compilacao concluida!"
    echo ""

    java -cp bin main.Main "$@"
    exit $?
fi
