#!/bin/bash

# Definição de caminhos
CK_JAR="tools/ck.jar"
SOURCE_DIR="src/main/java"
OUTPUT_DIR="target/ck-results"

echo "=========================================="
echo "     RODANDO ANALISE DE METRICAS (CK)     "
echo "=========================================="

# Cria o diretório de saída se não existir
if [ ! -d "$OUTPUT_DIR" ]; then
    mkdir -p "$OUTPUT_DIR"
fi

# Executa o CK
# Sintaxe: java -jar ck.jar <src> <use-jars> <max-files> <vars> <output>
java -jar "$CK_JAR" "$SOURCE_DIR" false 0 false "$OUTPUT_DIR/"

echo ""
echo "[OK] Analise concluida!"
echo "Os relatorios CSV estao na pasta: $OUTPUT_DIR/"