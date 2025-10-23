package main.view;

import java.util.List;
import java.util.Scanner;

import main.controller.BookController;
import main.model.Book;

public class BookView {
    private static Scanner scanner = new Scanner(System.in);

    public static void cadastrarLivro(BookController bookService) {
        System.out.println("\n--- CADASTRO DE LIVRO ---");
        
        System.out.print("Digite o título: ");
        String title = scanner.nextLine();
        
        System.out.print("Digite o autor: ");
        String author = scanner.nextLine();
        
        System.out.print("Digite o ISBN: ");
        String isbn = scanner.nextLine();
        
        try {
            Book book = new Book(title, author, isbn);
            bookService.addBook(book);
            System.out.println("Livro cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar livro: " + e.getMessage());
        }
    }

    public static void listarLivros(BookController bookService) {
        System.out.println("\n--- LISTA DE LIVROS CADASTRADOS ---");
        List<Book> books = bookService.getAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("Nenhum livro cadastrado no sistema.");
        } else {
            System.out.println("Total de livros: " + books.size());
            System.out.println();
            
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                System.out.printf("%d. Título: %-30s Autor: %-20s ISBN: %s%n", 
                    (i + 1), 
                    book.getTitle(), 
                    book.getAuthor(), 
                    book.getIsbn()
                );
            }
        }
    }

    public static void buscarLivroPorIsbn(BookController bookService) {
        System.out.println("\n--- BUSCAR LIVRO POR ISBN ---");
        System.out.print("Digite o ISBN do livro: ");
        String isbn = scanner.nextLine();
        
        Book book = bookService.findBookByIsbn(isbn);
        
        if (book != null) {
            System.out.println("Livro encontrado:");
            System.out.println("  Título: " + book.getTitle());
            System.out.println("  Autor: " + book.getAuthor());
            System.out.println("  ISBN: " + book.getIsbn());
        } else {
            System.out.println("Livro com ISBN '" + isbn + "' não encontrado.");
        }
    }


    public static void removerLivroPorIsbn(BookController bookService) {
        System.out.println("\n--- REMOVER LIVRO POR ISBN ---");
        System.out.print("Digite o ISBN do livro a ser removido: ");
        String isbn = scanner.nextLine();
        
        boolean removido = bookService.removeBookByIsbn(isbn);
        
        if (removido) {
            System.out.println("Livro removido com sucesso!");
        } else {
            System.out.println("Livro com ISBN '" + isbn + "' não encontrado. Nenhum livro foi removido.");
        }
    }
    
}
