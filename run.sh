#!/bin/bash

if [ ! -d "bin" ]; then
    mkdir bin
fi

if [ "$1" = "test" ] || [ "$1" = "tests" ]; then
    # Compilar e executar os testes
    javac -encoding UTF-8 -d bin src/main/model/*.java src/main/model/enums/*.java src/main/util/*.java src/main/controller/*.java src/test/util/*.java src/test/controller/*.java src/test/view/*.java src/test/TestRunner.java

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
    javac -encoding UTF-8 -d bin src/main/Main.java src/main/model/*.java src/main/model/enums/*.java src/main/controller/*.java src/main/view/*.java src/main/util/*.java

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
