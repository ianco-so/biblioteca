package main;

import main.controller.BookController;
import main.controller.LoanController;
import main.controller.UserController;
import main.view.LoanView;

import java.util.Scanner;

import static main.view.BookView.*;
import static main.view.UserView.cadastrarUsuario;
import static main.view.UserView.listarUsuarios;

public class Main {
    private static BookController bookService = new BookController();
    private static UserController userService = new UserController();
    private static LoanController loanService = new LoanController(bookService, userService);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   SISTEMA DE BIBLIOTECA - BOAS PRÁTICAS   ");
        System.out.println("===========================================");

        boolean continuar = true;
        //Adicionar nova opcao que cadastra alguns livros e usuarios automaticamente ?
        while (continuar) {
            exibirMenu();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarLivro(bookService);
                    break;
                case 2:
                    listarLivros(bookService);
                break;
                case 3:
                    buscarLivroPorIsbn(bookService);
                break;
                case 4:
                    removerLivroPorIsbn(bookService);
                    break;
                case 5:
                    cadastrarUsuario(userService);
                    break;
                case 6:
                    listarUsuarios(userService);
                    break;
                case 7:
                    LoanView.menu(loanService);
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Obrigado por usar o Sistema de Biblioteca!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

            if (continuar) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===========================================");
        System.out.println("               MENU PRINCIPAL");
        System.out.println("===========================================");
        System.out.println("1. Cadastrar Livro");
        System.out.println("2. Listar Livros");
        System.out.println("3. Buscar Livro por ISBN");
        System.out.println("4. Remover Livro por ISBN");
        System.out.println("5. Cadastrar usuário");
        System.out.println("6. Listar usuários");
        System.out.println("7. Empréstimos");
        System.out.println("0. Sair");
        System.out.println("===========================================");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
