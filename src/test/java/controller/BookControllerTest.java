package test.java.controller;

import main.java.controller.BookController;
import main.java.model.Book;

import java.util.List;
import java.util.Optional;

public class BookControllerTest {

    public static void main(String[] args) {
        BookControllerTest test = new BookControllerTest();
        
        System.out.println("=== Testes do BookController ===");
        
        System.out.println("\n\t\taddBook()");
        test.testAddBook();
        test.testAddBookWithDuplicateIsbn();
        test.testAddBookWithInvalidIsbn();

        System.out.println("\n\t\tgetAllBooks()");
        test.testGetAllBooks();
        
        System.out.println("\n\t\tfindByIsbn()");
        test.testFindByIsbn();
        test.testFindByIsbnNotFound();
        test.testFindByIsbnWithFormatting();
        
        System.out.println("\n\t\thasBook()");
        test.testHasBook();
        
        System.out.println("\n\t\tremoveByIsbn()");
        test.testRemoveByIsbn();
        test.testRemoveByIsbnNotFound();
        
        System.out.println("\n==========================");
    }

    private void printSuccess() {
        System.out.println("success!");
    }

    // ========== Testes de addBook ==========

    public void testAddBook() {
        System.out.print("testAddBook: ");
        
        BookController controller = new BookController();
        Book book = controller.addBook(
            "Clean Code",
            "978-0132350884",
            "Robert Martin",
            "Americana",
            5,
            true
        );
        
        assert book != null : "Livro deveria ter sido criado";
        assert book.getTitle().equals("Clean Code") : "Título incorreto";
        assert controller.getAllBooks().size() == 1 : "Deveria ter 1 livro";
        
        printSuccess();
    }

    public void testAddBookWithDuplicateIsbn() {
        System.out.print("testAddBookWithDuplicateIsbn: ");
        
        BookController controller = new BookController();
        controller.addBook(
            "Book 1", 
            "978-0132350884", 
            "Author 1", 
            "País 1", 
            5, 
            true
        );
        
        try {
            controller.addBook(
                "Book 2",
                "978-0132350884", 
                "Author 2", 
                "País 2", 
                3, 
                false
            );
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("já existe") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    public void testAddBookWithInvalidIsbn() {
        System.out.print("testAddBookWithInvalidIsbn: ");
        
        BookController controller = new BookController();
        
        try {
            controller.addBook(
                "Clean Code", 
                "123-invalid", 
                "Robert Martin", 
                "Americana", 
                5, 
                true
            );
            assert false : "Deveria lançar IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("ISBN inválido") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    // ========== Testes de getAllBooks ==========

    public void testGetAllBooks() {
        System.out.print("testGetAllBooks: ");
        
        BookController controller = new BookController();
        controller.addBook(
            "Book 1", 
            "978-0132350884", 
            "Author 1", 
            "País 1", 
            5, 
            true
        );
        controller.addBook(
            "Book 2", 
            "978-0306406157", 
            "Author 2", 
            "País 2", 
            3, 
            false
        );
        
        List<Book> books = controller.getAllBooks();
        
        assert books.size() == 2 : "Deveria ter 2 livros";
        assert books.get(0).getTitle().equals("Book 1") : "Primeiro livro incorreto";
        assert books.get(1).getTitle().equals("Book 2") : "Segundo livro incorreto";
        
        printSuccess();
    }

    // ========== Testes de findByIsbn ==========

    public void testFindByIsbn() {
        System.out.print("testFindByIsbn: ");
        
        BookController controller = new BookController();
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        Optional<Book> found = controller.findByIsbn("9780132350884");
        
        assert found.isPresent() : "Livro deveria ter sido encontrado";
        assert found.get().getTitle().equals("Clean Code") : "Livro incorreto";
        
        printSuccess();
    }

    public void testFindByIsbnNotFound() {
        System.out.print("testFindByIsbnNotFound: ");
        
        BookController controller = new BookController();
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        Optional<Book> found = controller.findByIsbn("9780000000000");
        
        assert found.isEmpty() : "Nenhum livro deveria ter sido encontrado";
        
        printSuccess();
    }

    public void testFindByIsbnWithFormatting() {
        System.out.print("testFindByIsbnWithFormatting: ");
        
        BookController controller = new BookController();
        controller.addBook(
            "Clean Code", 
            "9780132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        Optional<Book> found1 = controller.findByIsbn("978-0-13235-088-4");
        Optional<Book> found2 = controller.findByIsbn("978 0 13235 088 4");
        
        assert found1.isPresent() : "Deveria encontrar com hífens";
        assert found2.isPresent() : "Deveria encontrar com espaços";
        
        printSuccess();
    }

    // ========== Testes de hasBook ==========

    public void testHasBook() {
        System.out.print("testHasBook: ");
        
        BookController controller = new BookController();
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        boolean has1 = controller.hasBook("9780132350884");
        boolean has2 = controller.hasBook("978-0-13235-088-4");
        boolean has3 = controller.hasBook("9780000000000");
        
        assert has1 == true : "Deveria ter o livro";
        assert has2 == true : "Deveria encontrar com hífens";
        assert has3 == false : "Não deveria ter este livro";
        
        printSuccess();
    }

    // ========== Testes de removeByIsbn ==========

    public void testRemoveByIsbn() {
        System.out.print("testRemoveByIsbn: ");
        
        BookController controller = new BookController();
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        controller.addBook(
            "Design Patterns", 
            "978-0306406157", 
            "Gang of Four", 
            "Vários", 
            3, 
            false
        );
        
        boolean removed = controller.removeByIsbn("9780132350884");
        
        assert removed == true : "Livro deveria ter sido removido";
        assert controller.getAllBooks().size() == 1 : "Deveria restar 1 livro";
        assert controller.hasBook("9780132350884") == false : "Livro não deveria mais existir";
        
        printSuccess();
    }

    public void testRemoveByIsbnNotFound() {
        System.out.print("testRemoveByIsbnNotFound: ");
        
        BookController controller = new BookController();
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        boolean removed = controller.removeByIsbn("9780000000000");
        
        assert removed == false : "Remoção deveria ter falhado";
        assert controller.getAllBooks().size() == 1 : "Livro não deveria ter sido removido";
        
        printSuccess();
    }
}
