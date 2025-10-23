package main;

import static main.view.BookView.buscarLivroPorIsbn;
import static main.view.BookView.cadastrarLivro;
import static main.view.BookView.listarLivros;
import static main.view.BookView.removerLivroPorIsbn;

import java.util.Scanner;

import main.controller.BookController;

public class Main {
    private static BookController bookService = new BookController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   SISTEMA DE BIBLIOTECA - BOAS PRÁTICAS   ");
        System.out.println("===========================================");
        
        boolean continuar = true;
        
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
        System.out.println("0. Sair");
        System.out.println("===========================================");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}