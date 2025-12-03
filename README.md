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

O projeto possui uma suíte com vários testes:

- **IsbnValidatorTest** (10 testes).

- **BookControllerTest** (10 testes).

- **UserControllerTest** (8 testes).

- **LoanControllerTest** (16 testes).

- **Testes de Integração** (3 testes) (Esses são bem simples mesmo)
  - BookViewTest, UserViewTest, LoanViewTest

## 🚀 Como Executar

### Pré-requisitos

- **Java 25 LTS** (instalado e configurado no PATH)
    **Obs:** Também foi testado com Java 21, mas o projeto foi atualizado para Java 25 LTS.
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
mvn clean package
java -jar target/biblioteca-1.0.0.jar
```

#### Executar com dados de exemplo (seed)
```powershell
java -jar target\biblioteca-1.0.0.jar --seed
```

#### Executar os testes
```powershell
mvn test
```

## 📊 Análise de Código e Relatórios

O projeto possui **6 ferramentas** de análise de código configuradas:
- **Checkstyle** - Estilo de código (Google Style Guide)
- **PMD** - Análise estática e code smells
- **CPD** - Detecção de código duplicado
- **SpotBugs** - Detecção de bugs por análise de bytecode
- **JDepend** - Métricas de design e dependências entre pacotes
- **SonarQube** - Dashboard centralizado

📖 **Para instruções detalhadas de como gerar e visualizar os relatórios, consulte: [RUN_REPORTS.md](RUN_REPORTS.md)**

### Comandos Rápidos

```powershell
## 📊 Análise de Código e Relatórios

O projeto utiliza 6 ferramentas de análise: Checkstyle, PMD, CPD, SpotBugs, JDepend e SonarQube.

### Gerar Relatórios

```powershell
# Gerar todos os relatórios HTML
mvn clean compile site

# Abrir no navegador (Windows)
start target\site\project-reports.html
# Abrir no navegador (Linux/Mac)
xdg-open target/site/project-reports.html
```

Os relatórios ficam em `target/site/project-reports.html`

📖 **Para mais detalhes e outras opções, consulte: [RUN_REPORTS.md](RUN_REPORTS.md)**

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
