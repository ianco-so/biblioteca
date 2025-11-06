package test.view;

import main.controller.BookController;

public class BookViewTest {

    public static void main(String[] args) {
        BookViewTest test = new BookViewTest();
        
        System.out.println("=== Testes do BookView ===");
        
        System.out.println("\n\t\tTeste básico");
        test.testBookControllerIntegration();
        
        System.out.println("\n==========================");
    }

    private void printSuccess() {
        System.out.println("success!");
    }

    public void testBookControllerIntegration() {
        System.out.print("testBookControllerIntegration: ");
        
        BookController controller = new BookController();
        
        controller.addBook(
            "Clean Code",
            "978-0132350884",
            "Robert Martin",
            "Americana",
            5,
            true
        );
        
        var books = controller.getAllBooks();
        assert books.size() == 1 : "Controller deveria ter 1 livro";
        assert books.get(0).getTitle().equals("Clean Code") : "Título incorreto";
        
        printSuccess();
    }
}
