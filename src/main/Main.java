package main;

import main.controller.BookController;
import main.controller.LoanController;
import main.controller.UserController;

import main.view.BookView;
import main.view.LoanView;
import main.view.MenuView;
import main.view.UserView;

import java.util.Scanner;

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

        while (continuar) {
            showMenu();
            int option = MenuView.readOption();

            switch (option) {
                case 1:
                    BookView.menu(bookService);
                    break;
                case 2:
                    UserView.menu(userService);
                    break;
                case 3:
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

    private static void showMenu() {
        System.out.println("\n===========================================");
        System.out.println("               MENU PRINCIPAL");
        System.out.println("===========================================");
        System.out.println("1. Livros: ");
        System.out.println("2. Usuários: ");
        System.out.println("3. Empréstimos: ");
        System.out.println("0. Sair");
        System.out.println("===========================================");
        System.out.print("Escolha uma opção: ");
    }
}
