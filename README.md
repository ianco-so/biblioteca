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
│   │   ├── Main.java
│   │   ├── model/
│   │   │   ├── Author.java
│   │   │   ├── Book.java
│   │   │   ├── User.java
│   │   │   ├── Loan.java
│   │   │   └── enums/
│   │   │       └── LoanFilter.java
│   │   ├── controller/
│   │   │   ├── BookController.java
│   │   │   ├── UserController.java
│   │   │   └── LoanController.java
│   │   ├── view/
│   │   │   ├── MenuView.java
│   │   │   ├── BookView.java
│   │   │   ├── UserView.java
│   │   │   └── LoanView.java
│   │   └── util/
│   │       ├── DatabaseSeeder.java
│   │       └── IsbnValidator.java
│   └── test/
│       ├── util/
│       │   └── IsbnValidatorTest.java
│       ├── controller/
│       │   ├── BookControllerTest.java
│       │   ├── UserControllerTest.java
│       │   └── LoanControllerTest.java
│       ├── view/
│       │   ├── BookViewTest.java
│       │   ├── UserViewTest.java
│       │   └── LoanViewTest.java
│       └── TestRunner.java
├── bin/                    (gerado após compilação)
├── run.bat                 (script Windows)
├── run.sh                  (script Linux/Mac)
└── README.md      (você está aqui)
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

- Java 8 ou superior

### Opção 1: Scripts de Execução (se você é nutela e não quer complicação)

- :rocket: No Windows: execute o arquivo `run.bat`
```powershell
.\run.bat
```
- No Linux/Mac: execute o arquivo `run.sh`
```bash
./run.sh
```
- :card_file_box: Para executar com dados de exemplo (seed), adicione a flag `--seed` ou `-s`:
```powershell
.\run.bat --seed
```
```bash
./run.sh --seed
```
- :test_tube: Para executar os testes, use o comando `test`:
```powershell
.\run.bat test
```
```bash
./run.sh test
```
### Opção 2: Já que você é hardcore e gosta de dor de cabeça, faça manualmente

- Compilação (Windows/Linux/Mac):
```bash
javac -encoding UTF-8 -d bin src/main/Main.java src/main/model/*.java src/main/model/enums/*.java src/main/controller/*.java src/main/view/*.java src/main/util/*.java
```

- Execução
- :rocket: Sem seed:
```bash
java -cp bin main.Main
```
- :card_file_box: Com seed:
```bash
java -cp bin main.Main --seed
```
- Compilação dos testes:
```bash
javac -encoding UTF-8 -d bin src/main/model/*.java src/main/model/enums/*.java src/main/util/*.java src/main/controller/*.java src/test/util/*.java src/test/controller/*.java src/test/view/*.java src/test/TestRunner.java
```
- :test_tube: Execução dos testes:
```bash
java -ea -cp bin test.TestRunner
```

## 🎯 Boas Práticas Aplicadas

- **Arquitetura MVC** - Separação clara em Model, View, Controller
- **Encapsulamento** - Atributos privados com getters/setters apropriados
- **Validação de Dados** - Validação robusta em todas as camadas
  - ISBN-10 e ISBN-13 com algoritmo de checksum
  - Validação de IDs de usuário (alfanumérico)
  - Validação de prazos e datas
- **Tratamento de Exceções** - Exceções específicas e mensagens claras
- **Testes Automatizados** - 47 testes unitários e de integração
- **Imutabilidade** - Uso de `Optional`, `List.copyOf()`, records
- **Código Limpo** - Nomenclatura clara, métodos pequenos e focados
- **Documentação** - JavaDoc em métodos públicos
- **Streams e Programação Funcional** - Uso de Streams API quando apropriado
- **Enums** - Para valores fixos (LoanFilter)
- **Utilities** - Classes utilitárias estáticas (IsbnValidator, DatabaseSeeder)
