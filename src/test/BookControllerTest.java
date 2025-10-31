package test;

import main.controller.BookController;
import main.model.Book;

import java.util.List;

public class BookControllerTest {
    private BookController controller;

    public void beforeEach() {
        controller = new BookController();
    }

    public static void main(String[] args) {
        BookControllerTest test = new BookControllerTest();

        System.out.println("=== EXECUTANDO TESTES DO BOOKCONTROLLER ===\n");

        test.testAddBook();
        test.testAddNullBook();
        test.testGetAllBooks();
        test.testGetAllBooksReturnsNewList();
        test.testFindBookByIsbn();
        test.testFindBookByIsbnNotFound();
        test.testRemoveBookByIsbn();
        test.testRemoveBookByIsbnNotFound();
        test.testAddMultipleBooks();
        test.testRemoveFromMultipleBooks();

        System.out.println("\n=== TODOS OS TESTES CONCLUÍDOS ===");
    }

    public void testAddBook() {
        beforeEach();
        System.out.println("Teste: testAddBook");

        controller.addBook("Clean Code", "978-0132350884", "Robert C. Martin", "Norte-americano");

        List<Book> books = controller.getAllBooks();

        assert books.size() == 1 : "Esperado 1 livro, mas encontrado " + books.size();
        assert books.get(0).getTitle().equals("Clean Code") : "Título incorreto";
        assert books.get(0).getAuthor().getName().equals("Robert C. Martin") : "Autor incorreto";
        assert books.get(0).getIsbn().equals("978-0132350884") : "ISBN incorreto";

        System.out.println("  (V) Livro adicionado com sucesso\n");
    }

    public void testAddNullBook() {
        beforeEach();
        System.out.println("Teste: testAddNullBook");

        try {
            controller.addBook(null, "123", "Autor Qualquer", "Brasileira");
            assert false : "Deveria ter lançado IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // a mensagem vem do controller atual
            System.out.println("  (V) Exceção lançada corretamente ao tentar cadastrar com título nulo\n");
        }
    }

    public void testGetAllBooks() {
        beforeEach();
        System.out.println("Teste: testGetAllBooks");

        controller.addBook("Java Effective", "978-0134685991", "Joshua Bloch", "Norte-americano");
        controller.addBook("Design Patterns", "978-0201633610", "Gang of Four", "Vários");

        List<Book> books = controller.getAllBooks();

        assert books.size() == 2 : "Esperado 2 livros, mas encontrado " + books.size();
        assert books.get(0).getTitle().equals("Java Effective") : "Primeiro livro incorreto";
        assert books.get(1).getTitle().equals("Design Patterns") : "Segundo livro incorreto";

        System.out.println("  (V) Todos os livros retornados corretamente\n");
    }

    public void testGetAllBooksReturnsNewList() {
        beforeEach();
        System.out.println("Teste: testGetAllBooksReturnsNewList");

        controller.addBook("Test Driven Development", "978-0321146533", "Kent Beck", "Norte-americano");

        List<Book> books1 = controller.getAllBooks();
        List<Book> books2 = controller.getAllBooks();

        assert books1 != books2 : "getAllBooks deve retornar uma nova lista";
        assert books1.size() == books2.size() : "As listas devem ter o mesmo tamanho";

        System.out.println("  (V) getAllBooks retorna uma nova lista (encapsulamento correto)\n");
    }

    public void testFindBookByIsbn() {
        beforeEach();
        System.out.println("Teste: testFindBookByIsbn");

        controller.addBook("Refactoring", "978-0201485677", "Martin Fowler", "Britânico");
        controller.addBook("Code Complete", "978-0735619678", "Steve McConnell", "Norte-americano");

        Book found = controller.findBookByIsbn("978-0735619678");

        assert found != null : "Livro deveria ter sido encontrado";
        assert found.getTitle().equals("Code Complete") : "Livro incorreto encontrado";
        assert found.getAuthor().getName().equals("Steve McConnell") : "Autor incorreto";

        System.out.println("  (V) Livro encontrado por ISBN com sucesso\n");
    }

    public void testFindBookByIsbnNotFound() {
        beforeEach();
        System.out.println("Teste: testFindBookByIsbnNotFound");

        controller.addBook("The Pragmatic Programmer", "978-0201616224", "Andrew Hunt", "Norte-americano");

        Book found = controller.findBookByIsbn("978-9999999999");

        assert found == null : "Nenhum livro deveria ter sido encontrado";

        System.out.println("  (V) Retornou null para ISBN não encontrado\n");
    }

    public void testRemoveBookByIsbn() {
        beforeEach();
        System.out.println("Teste: testRemoveBookByIsbn");

        controller.addBook("Domain-Driven Design", "978-0321125217", "Eric Evans", "Norte-americano");

        boolean removed = controller.removeBookByIsbn("978-0321125217");

        assert removed : "Livro deveria ter sido removido";
        assert controller.getAllBooks().size() == 0 : "Lista deveria estar vazia";

        System.out.println("  (V) Livro removido por ISBN com sucesso\n");
    }

    public void testRemoveBookByIsbnNotFound() {
        beforeEach();
        System.out.println("Teste: testRemoveBookByIsbnNotFound");

        controller.addBook("Microservices", "978-1491950357", "Sam Newman", "Britânico");

        boolean removed = controller.removeBookByIsbn("978-0000000000");

        assert !removed : "Remoção deveria ter falhado";
        assert controller.getAllBooks().size() == 1 : "Livro não deveria ter sido removido";

        System.out.println(" (V) Retornou false para ISBN não encontrado na remoção\n");
    }

    public void testAddMultipleBooks() {
        beforeEach();
        System.out.println("Teste: testAddMultipleBooks");

        for (int i = 1; i <= 5; i++) {
            controller.addBook("Livro " + i, "ISBN-" + i, "Autor " + i, "Brasileiro");
        }

        List<Book> books = controller.getAllBooks();
        assert books.size() == 5 : "Esperado 5 livros, mas encontrado " + books.size();

        System.out.println("  (V) Múltiplos livros adicionados com sucesso\n");
    }

    public void testRemoveFromMultipleBooks() {
        beforeEach();
        System.out.println("Teste: testRemoveFromMultipleBooks");

        controller.addBook("Livro A", "ISBN-A", "Autor A", "BR");
        controller.addBook("Livro B", "ISBN-B", "Autor B", "BR");
        controller.addBook("Livro C", "ISBN-C", "Autor C", "BR");

        boolean removed = controller.removeBookByIsbn("ISBN-B");

        assert removed : "Livro deveria ter sido removido";
        assert controller.getAllBooks().size() == 2 : "Esperado 2 livros restantes";
        assert controller.findBookByIsbn("ISBN-B") == null : "Livro B não deveria mais existir";
        assert controller.findBookByIsbn("ISBN-A") != null : "Livro A deveria ainda existir";
        assert controller.findBookByIsbn("ISBN-C") != null : "Livro C deveria ainda existir";

        System.out.println("  (V) Livro removido corretamente de uma lista com múltiplos livros\n");
    }
}
