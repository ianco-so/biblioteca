package main.view;

import main.controller.BookController;
import main.model.Book;
import main.util.IsbnValidator;

import java.util.List;
import java.util.Optional;

public class BookView implements MenuView {

    public static void menu(BookController bookService) {
        var goBack = false;

        while (!goBack) {
            showMenuOptions();
            var opcao = MenuView.readOption();

            switch (opcao) {
                case 1:
                    createBook(bookService);
                    break;
                case 2:
                    getBooks(bookService);
                    break;
                case 3:
                    getBookByIsbn(bookService);
                    break;
                case 4:
                    removeBookByIsbn(bookService);
                    break;
                case 0:
                    goBack = true;
                    System.out.println("Voltando ao menu anterior...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void createBook(BookController bookService) {
        System.out.println("\n--- CADASTRO DE LIVRO ---");

        System.out.print("Digite o título: ");
        String title = scanner.nextLine();

        Optional<String> isbnOpt = promptForValidIsbn("Digite o ISBN: ");
        if (isbnOpt.isEmpty()) {
            return;
        }
        String isbn = isbnOpt.get(); 

        System.out.print("Digite o nome do autor: ");
        String authorName = scanner.nextLine();

        System.out.print("Digite a nacionalidade do autor: ");
        String nationality = scanner.nextLine();

        System.out.print("Digite a quantidade cópias do livro físico, do contrario apenas aperte enter: ");
        String copiesStr = scanner.nextLine().trim();

        int numberOfCopies = 0;
        if (!copiesStr.isEmpty()){
            numberOfCopies = Integer.parseInt(copiesStr);
        }

        System.out.print("Se o livro tem versão digital digite qualquer letra ou número, do contrario apenas aperte enter: ");
        String digitalAvailaString = scanner.nextLine();
        boolean digitalAvailability = false;

        if(!digitalAvailaString.trim().isEmpty()) {
            digitalAvailability = true;
        }

        if (bookService.hasBook(isbn)) {
            System.out.println("Esse livro já está cadastrado!");
        } else {
            try {
                bookService.addBook(title, isbn, authorName, nationality, numberOfCopies, digitalAvailability);
                System.out.println("Livro cadastrado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao cadastrar livro: " + e.getMessage());
            }
        }
    }

    private static void getBooks(BookController bookService) {
        System.out.println("\n--- LISTA DE LIVROS CADASTRADOS ---");
        List<Book> books = bookService.getAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("Nenhum livro cadastrado no sistema.");
        } else {
            System.out.println("Total de livros: " + books.size());
            System.out.println();
            
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                System.out.printf("%d. Título: %-30s Autor: %-20s ISBN: %s%n Número de Cópias: %-20d Está disponível digitalmente: %b", 
                    (i + 1), 
                    book.getTitle(), 
                    book.getAuthor(), 
                    book.getIsbn(),
                    book.getNumberOfCopies(),
                    book.getDigitalAvailability()
                );
            }
        }
    }

    private static Optional<String> promptForValidIsbn(String promptMessage) {
        System.out.print(promptMessage);
        String isbn = scanner.nextLine();

        while (!IsbnValidator.isValid(isbn)) {
            System.out.println("ISBN inválido! Tente novamente ou Digite 'sair' para cancelar.");
            System.out.print(promptMessage);
            
            isbn = scanner.nextLine();
            
            if (isbn.equalsIgnoreCase("sair")) {
                return Optional.empty(); // O usuário cancelou
            }
        }
        
        // Sucesso! Retorna o ISBN limpo.
        return Optional.of(IsbnValidator.getCleanIsbn(isbn));
    }

    private static void getBookByIsbn(BookController bookService) {
        System.out.println("\n--- BUSCAR LIVRO POR ISBN ---");
        
        var isbnOpt = promptForValidIsbn("Digite o ISBN do livro: ");
        if (isbnOpt.isEmpty()) {
            return;
        }
        
        var book = bookService.findByIsbn(isbnOpt.get());
        
        if (book.isPresent()) {
            System.out.println("Livro encontrado:");
            System.out.println("  Título: " + book.get().getTitle());
            System.out.println("  Autor: " + book.get().getAuthor());
            System.out.println("  ISBN: " + book.get().getIsbn());
        } else {
            System.out.println("Livro com ISBN '" + isbnOpt.get() + "' não encontrado.");
        }
    }

    private static void removeBookByIsbn(BookController bookService) {
        System.out.println("\n--- REMOVER LIVRO POR ISBN ---");
        var isbnOpt = promptForValidIsbn("Digite o ISBN do livro a ser removido: ");
        if (isbnOpt.isEmpty()) {
            return;
        }
        var isbn = isbnOpt.get();
        
        var removido = bookService.removeByIsbn(isbn);
        
        if (removido) {
            System.out.println("Livro removido com sucesso!");
        } else {
            System.out.println("Livro com ISBN '" + isbn + "' não encontrado. Nenhum livro foi removido.");
        }
    }

    private static void showMenuOptions() {
        System.out.println("\n=== MENU DE LIVROS ===");
        System.out.println("1. Cadastrar Livro");
        System.out.println("2. Listar Livros");
        System.out.println("3. Buscar Livro por ISBN");
        System.out.println("4. Remover Livro por ISBN");
        System.out.println("0. Voltar");
    }
}
