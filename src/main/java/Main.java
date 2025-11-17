package main.java;

import main.java.controller.BookController;
import main.java.controller.LoanController;
import main.java.controller.UserController;
import main.java.util.DatabaseSeeder;

import main.java.view.BookView;
import main.java.view.LoanView;
import main.java.view.MenuView;
import main.java.view.UserView;

import java.util.Scanner;

public class Main {
    private static BookController bookService = new BookController();
    private static UserController userService = new UserController();
    private static LoanController loanService = new LoanController(bookService, userService);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (shouldLoadSeedData(args)) {
            System.out.println("Carregando dados de exemplo...");
            DatabaseSeeder seeder = new DatabaseSeeder(bookService, userService, loanService);
            seeder.seed();
        }
        
        System.out.println("===========================================");
        System.out.println("   SISTEMA DE BIBLIOTECA - BOAS PRÁTICAS   ");
        System.out.println("===========================================");

        boolean continueLoop = true;

        while (continueLoop) {
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
                    continueLoop = false;
                    System.out.println("Obrigado por usar o Sistema de Biblioteca!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }

            if (continueLoop) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static boolean shouldLoadSeedData(String[] args) {
        if (args.length == 0) {
            return false;
        }
        
        for (String arg : args) {
            if (arg.equals("--seed") || arg.equals("-s")) {
                return true;
            }
        }

        return false;
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
