# Sistema de Gerenciamento de Biblioteca

Este é um projeto da disciplina de Boas Práticas de Programação que implementa um sistema para uma biblioteca.

## 👥 Equipe

- Ianco Soares Oliveira
- Ian Lucas Melo Trindade
- Lucas Oliveira


## 📚 Sobre o Sistema

O sistema atualmente permite o CRUD de livros através de uma interface de linha de comando.

## ✨ Funcionalidades Implementadas (todas em versão inicial)

- ✅ Cadastro de livros (título, autor, ISBN)
- ✅ Listagem de todos os livros cadastrados
- ✅ Busca de livros por ISBN
- ✅ Remoção de livros por ISBN

## 📁 Estrutura do Projeto

```
biblioteca/
├── src/
│   ├── main/
│   │   ├── Main.java
│   │   ├── model/
│   │   │   └── Book.java
│   │   ├── controller/
│   │   │   └── BookController.java
│   │   └── view/
│   │       └── BookView.java
│   └── test/
│       ├── BookControllerTest.java
│       ├── BookViewTest.java
│       └── TestRunner.java
└── bin/                    (gerado após compilação)
```

## 🚀 Como Executar

### Pré-requisitos

- Java 8 ou superior

### Compilação

```powershell
javac -d bin src/main/Main.java src/main/model/*.java src/main/controller/*.java src/main/view/*.java
```

### Execução

```powershell
java -cp bin main.Main
```

## 🧪 Como Executar os Testes

### Compilar os testes

```powershell
javac -d bin src/main/model/*.java src/main/controller/*.java src/main/view/*.java src/test/*.java
```

### Executar todos os testes

```powershell
java -ea -cp bin test.TestRunner
```

**Nota:** A flag `-ea` é necessária para habilitar assertions.

## 🎯 Boas Práticas Aplicadas

- Separação em camadas (Model, View, Controller)
- Encapsulamento de dados
- Validação de entradas
- Tratamento de exceções
- Testes unitários
- Código limpo e legível
- Documentação com Javadoc
