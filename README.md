# Sistema de Gerenciamento de Biblioteca

Este é um projeto da disciplina de Boas Práticas de Programação que implementa um sistema para uma biblioteca.

## 👥 Equipe

- Ianco Soares Oliveira
- Ian Lucas Melo Trindade
- Lucas de Machado


## 📚 Sobre o Sistema

Sistema de gerenciamento de biblioteca que permite:
- Gerenciamento de livros (físicos e digitais) com validação de ISBN
- Cadastro e controle de usuários
- Sistema de empréstimos com controle de prazos
- Histórico de empréstimos por usuário
- Filtros de consulta (empréstimos abertos, fechados, todos)
- Interface de linha de comando intuitiva

## ✨ Funcionalidades Implementadas

### Livros
- ✅ Cadastro de livros (título, autor, ISBN, cópias físicas, disponibilidade digital)
- ✅ Validação de ISBN-10 e ISBN-13 com algoritmo de checksum
- ✅ Listagem de todos os livros cadastrados
- ✅ Busca de livros por ISBN (com suporte a formatação)
- ✅ Remoção de livros por ISBN

### Usuários
- ✅ Cadastro de usuários (nome, ID)
- ✅ Listagem de todos os usuários
- ✅ Consulta de histórico de empréstimos por usuário
- ✅ Validação de dados de entrada

### Empréstimos
- ✅ Empréstimo de livros físicos e digitais
- ✅ Devolução de livros
- ✅ Extensão de prazo de devolução
- ✅ Listagem com filtros (abertos, fechados, todos)
- ✅ Ordenação por data de empréstimo
- ✅ Controle de disponibilidade de cópias
- ✅ Validação de prazos máximos (60 dias)

## 📁 Estrutura do Projeto

```
biblioteca/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── main/
│   │   │   │   └── Main.java
│   │   │   ├── model/          (modelos de dados)
│   │   │   ├── controller/     (lógica de controle)
│   │   │   ├── view/           (interface de usuário)
│   │   │   └── util/
│   │   │       ├── DatabaseSeeder.java (seeding de dados de exemplo)
│   │   │       └── IsbnValidator.java (validação de ISBN)
│   │   └── resources/
│   │       ├── checkstyle/
│   │       │   └── google_checks.xml (configuração do Checkstyle)
│   │       
│   └── test/
│       └── java/
│           ├── test/
│           │   ├── util/         (testes dos utilitários)
│           │   ├── controller/   (testes dos controllers)
│           │   ├── view/         (testes das views)
│           │   └── TestRunner.java (executa todos os testes)
├── target/                 (gerado após compilação Maven)
├── .mvn/                   (Maven Wrapper)
├── mvnw                    (Maven Wrapper - Linux/Mac)
├── mvnw.cmd                (Maven Wrapper - Windows)
├── pom.xml                 (configuração Maven)
├── scripts/
│   ├── run.bat             (script Windows - legado)
│   └── run.sh              (script Linux/Mac - legado)
└── README.md               (você está aqui)
```

## 🧪 Testes

O projeto possui uma suíte com **47 testes** (testes que só):

- **IsbnValidatorTest** (10 testes)
  - Validação de ISBN-10 e ISBN-13
  - Testes de checksum
  - Casos extremos (null, vazio, formato inválido)

- **BookControllerTest** (10 testes)
  - CRUD completo de livros
  - Validação de ISBN duplicado
  - Busca com diferentes formatos

- **UserControllerTest** (8 testes)
  - Cadastro e busca de usuários
  - Validação de dados
  - Histórico de empréstimos

- **LoanControllerTest** (16 testes)
  - Empréstimos físicos e digitais
  - Devoluções e extensões
  - Filtros e ordenação
  - Validações de disponibilidade

- **Testes de Integração** (3 testes) (Esses são bem simples mesmo)
  - BookViewTest, UserViewTest, LoanViewTest

## 🚀 Como Executar

### Pré-requisitos

- **Java 25 LTS** (instalado e configurado no PATH)
    **Obs:** Se você não quiser instalar o java tudo bem, o projeto já vem com o Maven Wrapper que baixa uma versão do Java automaticamente.
- **Maven** (opcional - o projeto usa Maven Wrapper)

### Opção 1: Maven (Recomendado) ⭐

O projeto usa **Maven Wrapper**, então você **não precisa instalar Maven**!

#### Executar o programa diretamente
```powershell
# Windows
.\mvnw.cmd compile exec:java

# Linux/Mac
./mvnw compile exec:java

# Com Maven instalado globalmente
mvn compile exec:java
```

#### Ou gerar JAR e executar
```powershell
# Windows
.\mvnw.cmd clean package
java -jar target\biblioteca-1.0.0.jar

# Linux/Mac
./mvnw clean package
java -jar target/biblioteca-1.0.0.jar

# Com Maven instalado globalmente
mvn clean package
java -jar target/biblioteca-1.0.0.jar
```

#### Executar com dados de exemplo (seed)
```powershell
java -jar target\biblioteca-1.0.0.jar --seed
```

#### Executar os testes
```powershell
# Windows
.\mvnw.cmd test

# Linux/Mac
./mvnw test

# Maven
mvn test
```

### Opção 2: Scripts Legados (Compatibilidade)

Os scripts antigos ainda funcionam para execução direta:

```powershell
# Windows
.\scripts\run.bat

# Windows com seed
.\scripts\run.bat --seed

# Linux/Mac
./scripts/run.sh

# Linux/Mac com seed
./scripts/run.sh --seed
```

## 📊 Análise de Código e Relatórios

```powershell
# Verificar com checkstyle:
mvn checkstyle:check

# Executar análise PMD: detecta problemas de código, bugs potenciais e code smells:
mvn pmd:check

# Executar detecção de código duplicado (CPD):
mvn cpd:check

# Executar análise SpotBugs:
mvn spotbugs:check

# Roda O Spotbugs com GUI (interface gráfica interativa)
mvn spotbugs:gui

# Executar análise JDepend (métricas de design e dependências entre pacotes):
mvn jdepend:generate

# Gerar relatório completo com TODAS as análises (Checkstyle + PMD + CPD + SpotBugs + JDepend)
mvn clean compile site
```

### 🔍 SonarQube - Análise Centralizada

O SonarQube centraliza todas as análises em um dashboard interativo.

#### Opção 1: Docker (Recomendado) ⭐

**Requisito:** Docker instalado ([Download](https://www.docker.com/products/docker-desktop))

```powershell
# 1. Iniciar o servidor SonarQube
docker-compose up -d

# 2. Aguardar ~2 minutos até o servidor iniciar completamente
# Verificar se está pronto:
docker logs -f sonarqube-biblioteca

# 3. Acessar http://localhost:9000
# Login: admin / Senha: admin (vai pedir para trocar)

# 4. Criar projeto manualmente:
#    - Create Project → Manually
#    - Project key: biblioteca
#    - Display name: Sistema de Gerenciamento de Biblioteca
#    - Main branch: main
#    - Set Up → Locally
#    - Generate Token → Copiar o token

# 5. Executar análise (substitua SEU_TOKEN pelo token gerado)
mvn clean verify sonar:sonar -Dsonar.token=SEU_TOKEN

# 6. Parar o servidor
docker-compose down
```

#### Opção 2: Instalação Manual

**Requisito:** Java 17 instalado

```powershell
# 1. Baixar SonarQube Community Edition
# https://www.sonarsource.com/products/sonarqube/downloads/

# 2. Descompactar em C:\sonarqube (sem espaços no caminho)

# 3. Iniciar servidor (Windows)
C:\sonarqube\bin\windows-x86-64\StartSonar.bat

# 4. Aguardar até ver: "SonarQube is operational"

# 5. Acessar http://localhost:9000 e seguir passos 3-5 da Opção 1
```

#### Dashboard do SonarQube

Após a análise, você terá acesso a:

- 📊 **Dívida Técnica**: Tempo estimado para corrigir problemas
- 🐛 **Bugs**: Problemas que podem causar erros
- 🔒 **Vulnerabilidades**: Problemas de segurança
- 💡 **Code Smells**: Problemas de manutenibilidade
- 📈 **Cobertura de Testes**: Porcentagem de código testado
- 🔄 **Duplicação**: Código duplicado/copiado
- 📐 **Complexidade Ciclomática**: Medida de complexidade do código
- 📦 **Métricas por Pacote**: Análise detalhada da arquitetura

Para visualizar os relatórios offline:
```powershell
# Windows - abrir no navegador
start target\site\project-reports.html

# Linux/Mac
xdg-open target/site/project-reports.html  # Linux
open target/site/project-reports.html      # Mac
```

📄 Relatórios gerados em: `target/site/`
- `checkstyle.html` - Análise de estilo de código
- `pmd.html` - Análise estática PMD
- `cpd.html` - Detecção de código duplicado
- `spotbugs.html` - Análise de bugs potenciais (SpotBugs)
- `jdepend-report.html` - Métricas de design e dependências entre pacotes (JDepend)
- `project-reports.html` - Página índice com todos os relatórios

### Ferramentas de Análise de Código

O projeto utiliza **6 ferramentas** de análise de código:

1. **Checkstyle** - Verifica estilo de código seguindo Google Style Guide
2. **PMD** - Detecta problemas de código, bugs potenciais e code smells
3. **CPD** - Detecta código duplicado (Copy/Paste Detector)
4. **SpotBugs** - Encontra bugs através de análise de bytecode (inclui FindSecBugs para vulnerabilidades de segurança)
5. **JDepend** - Analisa qualidade do design e dependências entre pacotes
6. **SonarQube** - Dashboard centralizado que integra todas as ferramentas anteriores

**Categorias de regras PMD configuradas:**
- Best Practices (melhores práticas)
- Code Style (estilo de código)
- Design (design de código)
- Error Prone (propenso a erros)
- Multithreading (problemas de concorrência)
- Performance (otimizações)

### Configurações

- **Checkstyle**: Google Style Guide 12.1.2 (`src/main/resources/checkstyle/google_checks.xml`)
- **PMD**: Versão 7.18.0 com 6 categorias de regras
- **SpotBugs**: Versão 4.9.8 (inclui FindSecBugs 1.14.0)
- **JDepend**: Versão 2.1
- **SonarQube**: Plugin Maven 5.3.0 (requer servidor rodando)
- **Java**: 25

## 🎯 Boas Práticas Aplicadas

- **Arquitetura MVC** - Separação clara em Model, View, Controller
- **Gerenciamento de Dependências** - Maven para build, testes e plugins
- **Maven Wrapper** - Sem necessidade de instalação prévia do Maven
- **Java 25 LTS** - Versão Long-Term Support mais recente
- **Encapsulamento** - Atributos privados com getters/setters apropriados
- **Validação de Dados** - Validação robusta em todas as camadas
  - ISBN-10 e ISBN-13 com algoritmo de checksum
  - Validação de IDs de usuário (alfanumérico)
  - Validação de prazos e datas
- **Tratamento de Exceções** - Exceções específicas e mensagens claras
- **Testes Automatizados** - 49 testes unitários e de integração
- **Imutabilidade** - Uso de `Optional`, `List.copyOf()`, records
- **Código Limpo** - Nomenclatura clara, métodos pequenos e focados
- **Documentação** - JavaDoc em métodos públicos
- **Streams e Programação Funcional** - Uso de Streams API quando apropriado
- **Enums** - Para valores fixos (LoanFilter)
- **Utilities** - Classes utilitárias estáticas (IsbnValidator, DatabaseSeeder)
- **Análise Estática** - Checkstyle com Google Style Guide
