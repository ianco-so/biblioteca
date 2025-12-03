@echo off
echo ==========================================
echo      RODANDO ANALISE DE METRICAS (CK)
echo ==========================================

if not exist target\ck-results mkdir target\ck-results

REM Comando do CK: java -jar ck.jar <path-src> <use-jars> <max-files-per-partition> <variables-and-fields> <output-dir>
java -jar tools/ck.jar src/main/java false 0 false target/ck-results/

echo.
echo [OK] Analise concluida!
echo Os relatorios CSV estao na pasta: target/ck-results/
pause