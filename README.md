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
│   │   │   ├── model/
│   │   │   │   ├── Author.java
│   │   │   │   ├── Book.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Loan.java
│   │   │   │   └── enums/
│   │   │   │       └── LoanFilter.java
│   │   │   ├── controller/
│   │   │   │   ├── BookController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── LoanController.java
│   │   │   ├── view/
│   │   │   │   ├── MenuView.java
│   │   │   │   ├── BookView.java
│   │   │   │   ├── UserView.java
│   │   │   │   └── LoanView.java
│   │   │   └── util/
│   │   │       ├── DatabaseSeeder.java
│   │   │       └── IsbnValidator.java
│   │   └── resources/
│   │       └── checkstyle/
│   │           └── google_checks.xml
│   └── test/
│       └── java/
│           ├── test/
│           │   ├── util/
│           │   │   └── IsbnValidatorTest.java
│           │   ├── controller/
│           │   │   ├── BookControllerTest.java
│           │   │   ├── UserControllerTest.java
│           │   │   └── LoanControllerTest.java
│           │   ├── view/
│           │   │   ├── BookViewTest.java
│           │   │   ├── UserViewTest.java
│           │   │   └── LoanViewTest.java
│           │   └── TestRunner.java
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

- **Java 21 LTS** (instalado e configurado no PATH)
- **Maven** Opcional (não é necessário! O projeto usa Maven Wrapper (`mvnw`))

### Opção 1: Maven (Recomendado) ⭐

O projeto usa **Maven Wrapper**, então você **não precisa instalar Maven**!

<!-- #### Compilar o projeto (não cria o jar)
```powershell
# Windows
.\mvnw.cmd clean compile

# Linux/Mac
./mvnw clean compile

# Com maven instalado globalmente (esperto)
mvn clean compile
``` -->

#### Executar o programa
```powershell
# Windows
.\mvnw.cmd clean package
java -jar target\biblioteca-1.0.0.jar

# Linux/Mac
./mvnw clean package
java -jar target/biblioteca-1.0.0.jar

# Com maven instalado globalmente (esperto)
mvn clean package
java -jar target/biblioteca-1.0.0.jar
```

#### Executar com dados de exemplo (seed)
```powershell
# Compilar e gerar JAR
.\mvnw.cmd clean package

# Executar com seed
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

#### Verificar estilo de código (Checkstyle)
```powershell
# Windows
.\mvnw.cmd checkstyle:check

# Linux/Mac
./mvnw checkstyle:check

# Maven
mvn checkstyle:check
```

#### Gerar relatório HTML:
```powershell
# Windows
.\mvnw.cmd site

# Linux/Mac
./mvnw site

# Maven
mvn site
```
O relatório será gerado em `target/site/checkstyle.html`

### Opção 2: Scripts Legados (Compatibilidade)

Os scripts antigos ainda funcionam para execução direta:

- No Windows: execute o arquivo `run.bat`
```powershell
.\scripts\run.bat
```
- No Linux/Mac: execute o arquivo `run.sh`
```bash
./scripts/run.sh
```
- Para executar com dados de exemplo (seed), adicione a flag `--seed` ou `-s`:
```powershell
.\scripts\run.bat --seed
```
```bash
./scripts/run.sh --seed
```
- Para executar os testes, use o comando `test`:
```powershell
.\scripts\run.bat test
```
```bash
./scripts/run.sh test
```

#### Configuração:
- Arquivo de regras: `src/main/resources/checkstyle/google_checks.xml`
- Plugin Maven: `maven-checkstyle-plugin` versão 3.3.1
- Checkstyle: versão 12.1.2

## 🎯 Boas Práticas Aplicadas

- **Arquitetura MVC** - Separação clara em Model, View, Controller
- **Gerenciamento de Dependências** - Maven para build, testes e plugins
- **Maven Wrapper** - Sem necessidade de instalação prévia do Maven
- **Java 21 LTS** - Versão Long-Term Support mais recente
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
