package view;

import controller.BookController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do BookView")
class BookViewTest {

    @Test
    @DisplayName("Deve integrar com BookController corretamente")
    void testBookControllerIntegration() {
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
        assertEquals(1, books.size(), "Controller deveria ter 1 livro");
        assertEquals("Clean Code", books.get(0).getTitle(), "Título incorreto");
    }
}
