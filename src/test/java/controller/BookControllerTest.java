package controller;

import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do BookController")
class BookControllerTest {

    private BookController controller;

    @BeforeEach
    void setUp() {
        controller = new BookController();
    }

    // ========== Testes de addBook ==========

    @Test
    @DisplayName("Deve adicionar um livro com sucesso")
    void testAddBook() {
        Book book = controller.addBook(
            "Clean Code",
            "978-0132350884",
            "Robert Martin",
            "Americana",
            5,
            true
        );
        
        assertNotNull(book, "Livro deveria ter sido criado");
        assertEquals("Clean Code", book.getTitle(), "Título incorreto");
        assertEquals(1, controller.getAllBooks().size(), "Deveria ter 1 livro");
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar livro com ISBN duplicado")
    void testAddBookWithDuplicateIsbn() {
        controller.addBook(
            "Book 1", 
            "978-0132350884", 
            "Author 1", 
            "País 1", 
            5, 
            true
        );
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.addBook(
                "Book 2",
                "978-0132350884", 
                "Author 2", 
                "País 2", 
                3, 
                false
            )
        );
        
        assertTrue(exception.getMessage().contains("já existe"), "Mensagem de erro incorreta");
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar livro com ISBN inválido")
    void testAddBookWithInvalidIsbn() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> controller.addBook(
                "Clean Code", 
                "123-invalid", 
                "Robert Martin", 
                "Americana", 
                5, 
                true
            )
        );
        
        assertTrue(exception.getMessage().contains("ISBN inválido"), "Mensagem de erro incorreta");
    }

    // ========== Testes de getAllBooks ==========

    @Test
    @DisplayName("Deve retornar todos os livros cadastrados")
    void testGetAllBooks() {
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
        
        assertEquals(2, books.size(), "Deveria ter 2 livros");
        assertEquals("Book 1", books.get(0).getTitle(), "Primeiro livro incorreto");
        assertEquals("Book 2", books.get(1).getTitle(), "Segundo livro incorreto");
    }

    // ========== Testes de findByIsbn ==========

    @Test
    @DisplayName("Deve encontrar livro por ISBN")
    void testFindByIsbn() {
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        Optional<Book> found = controller.findByIsbn("9780132350884");
        
        assertTrue(found.isPresent(), "Livro deveria ter sido encontrado");
        assertEquals("Clean Code", found.get().getTitle(), "Livro incorreto");
    }

    @Test
    @DisplayName("Deve retornar vazio quando ISBN não for encontrado")
    void testFindByIsbnNotFound() {
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        Optional<Book> found = controller.findByIsbn("9780000000000");
        
        assertTrue(found.isEmpty(), "Nenhum livro deveria ter sido encontrado");
    }

    @Test
    @DisplayName("Deve encontrar livro por ISBN com formatação")
    void testFindByIsbnWithFormatting() {
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
        
        assertTrue(found1.isPresent(), "Deveria encontrar com hífens");
        assertTrue(found2.isPresent(), "Deveria encontrar com espaços");
    }

    // ========== Testes de hasBook ==========

    @Test
    @DisplayName("Deve verificar se livro existe por ISBN")
    void testHasBook() {
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        assertTrue(controller.hasBook("9780132350884"), "Deveria ter o livro");
        assertTrue(controller.hasBook("978-0-13235-088-4"), "Deveria encontrar com hífens");
        assertFalse(controller.hasBook("9780000000000"), "Não deveria ter este livro");
    }

    // ========== Testes de removeByIsbn ==========

    @Test
    @DisplayName("Deve remover livro por ISBN")
    void testRemoveByIsbn() {
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
        
        assertTrue(removed, "Livro deveria ter sido removido");
        assertEquals(1, controller.getAllBooks().size(), "Deveria restar 1 livro");
        assertFalse(controller.hasBook("9780132350884"), "Livro não deveria mais existir");
    }

    @Test
    @DisplayName("Deve retornar false ao tentar remover livro inexistente")
    void testRemoveByIsbnNotFound() {
        controller.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            5, 
            true
        );
        
        boolean removed = controller.removeByIsbn("9780000000000");
        
        assertFalse(removed, "Remoção deveria ter falhado");
        assertEquals(1, controller.getAllBooks().size(), "Livro não deveria ter sido removido");
    }
}
