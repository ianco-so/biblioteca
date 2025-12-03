# Guia: Como Gerar Relatórios de Análise de Código

Este guia explica, passo a passo, como gerar e visualizar os relatórios de análise de código do projeto.

## Arquivos e relatórios abordados:
- Checkstyle
- PMD
- CPD (detecção de código duplicado)
- SpotBugs
- JDepend
- SonarQube (dashboard centralizado)

## Pré-requisitos
- Java (compatível com sua configuração de desenvolvimento — o projeto usa Java 21/25 para compilação)
- Maven (ou usar o Maven Wrapper: `mvnw` / `mvnw.cmd` para Windows)
- Se usar SonarQube via Docker: Docker e Docker Compose instalados ou SonarQube instalado localmente.

## Passo a passo para gerar e visualizar os relatórios
### Gerar cada relatório individualmente

- Checkstyle
    ```powershell
    # Usando Maven (local)
    mvn checkstyle:check
    # Se usar Maven Wrapper (Windows)
    .\mvnw.cmd checkstyle:check
    # Se você usa Linux ou Mac, use:
    mvnw checkstyle:check
    ```
- PMD
    ```powershell
    mvn pmd:check
    ```

- CPD (Copy/Paste Detector)
    ```powershell
    mvn cpd:check
    ```

- SpotBugs
    **SpotBugs analisa bytecode, então compile antes!**

    ```powershell
    mvn spotbugs:check
    # GUI interativa (opcional)
    mvn spotbugs:gui
    ```

- JDepend
    ```powershell
    mvn jdepend:generate
    ```

### Gerar todos os relatórios HTML locais (site Maven)

SpotBugs precisa de classes compiladas. Execute `compile` antes do `site`.

```powershell
# comando recomendado (gera site com Checkstyle, PMD, CPD, SpotBugs, JDepend)
mvn clean compile site
# Se já tiver compilado (ou se não quiser o SpotBugs):
mvn clean site
```

Os relatórios HTML serão gerados em `target/site/`.
- `project-reports.html` — índice com os relatórios
### Abrir os relatórios locais

- Windows
    ```powershell
    start target\site\project-reports.html
    ```
    - Linux
    ```bash
    xdg-open target/site/project-reports.html
    ```
    - Mac
    ```bash
    open target/site/project-reports.html
    ```

## Enviar análise para SonarQube (opção centralizada)

Existem duas formas comuns de rodar o servidor SonarQube:
- Localmente (instalação manual)
- Via Docker (recomendado para facilidade)

### A) Usando Docker (recomendado)
```powershell
# Iniciar SonarQube (no diretório do projeto com docker-compose.yml)
docker-compose up -d
# Aguardar ~2 minutos e verificar logs
docker logs -f sonarqube-biblioteca
# Acesse http://localhost:9000 e crie o projeto se necessário
```

### B) Usando SonarQube local (instalação manual)
- Baixe SonarQube Community (ideia: usar a LTS 10.x)
- Extraia em `\sonarqube` e execute `StartSonar.bat` (Windows) ou `sonar.sh` (Linux/Mac)
    Nesse caso lembre-se que o SonaQube usa o Java (21 e 17 foram testados). Então certifique-se de estar com java o no seu ambiente.
- Acesse http://localhost:9000

### Executando a análise (envio para SonarQube):
Para cada versão do SonarQube isso pode ser diferente. Aqui está a forma para a versão indicada no docker-compose.yml (SonarQube 10.x LTS).
1) Crie um projeto no SonarQube (Projects → Create Manualy)
- Project Key: `biblioteca`
- Project Name: `Sistema de Gerenciamento de Biblioteca`
2) Gere um token de autenticação. Vá na página do seu projeto e clique em "Locally" e depois gere Token.
- Salve o token gerado (você não verá ele novamente)
- Forma simples (com token)
```powershell
mvn clean verify sonar:sonar "-Dsonar.login=SEU_TOKEN"
```

#### Observações importantes:
- No PowerShell use aspas ao redor de cada `-D` é necessário no powershell. Se você usar cmd.exe ou terminal Linux/Mac, não precisa das aspas.
- É importante dizer que isso muda conforme a versão do SonarQube. Consulte a documentação oficial se necessário.

## Compilando com o Error Prone da Google (Opcional):

Existe um perfil Maven chamado `analysis` que ativa o plugin Error Prone da Google para análise estática adicional durante a compilação.
Para usar, compile com o perfil `analysis`:
```powershell
mvn clean compile -P analysis
```
