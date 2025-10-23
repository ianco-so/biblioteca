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
        
        Book book = new Book("Clean Code", "Robert C. Martin", "978-0132350884");
        controller.addBook(book);
        
        List<Book> books = controller.getAllBooks();
        
        assert books.size() == 1 : "Esperado 1 livro, mas encontrado " + books.size();
        assert books.get(0).getTitle().equals("Clean Code") : "Título incorreto";
        assert books.get(0).getAuthor().equals("Robert C. Martin") : "Autor incorreto";
        assert books.get(0).getIsbn().equals("978-0132350884") : "ISBN incorreto";
        
        System.out.println("  (V) Livro adicionado com sucesso\n");
    }

    public void testAddNullBook() {
        beforeEach();
        System.out.println("Teste: testAddNullBook");
        
        try {
            controller.addBook(null);
            assert false : "Deveria ter lançado IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("O livro não pode ser nulo.") : "Mensagem de erro incorreta";
            System.out.println("  (V) Exceção lançada corretamente ao adicionar livro nulo\n");
        }
    }

    public void testGetAllBooks() {
        beforeEach();
        System.out.println("Teste: testGetAllBooks");
        
        Book book1 = new Book("Java Effective", "Joshua Bloch", "978-0134685991");
        Book book2 = new Book("Design Patterns", "Gang of Four", "978-0201633610");
        
        controller.addBook(book1);
        controller.addBook(book2);
        
        List<Book> books = controller.getAllBooks();
        
        assert books.size() == 2 : "Esperado 2 livros, mas encontrado " + books.size();
        assert books.get(0).getTitle().equals("Java Effective") : "Primeiro livro incorreto";
        assert books.get(1).getTitle().equals("Design Patterns") : "Segundo livro incorreto";
        
        System.out.println("  (V) Todos os livros retornados corretamente\n");
    }

    public void testGetAllBooksReturnsNewList() {
        beforeEach();
        System.out.println("Teste: testGetAllBooksReturnsNewList");
        
        Book book = new Book("Test Driven Development", "Kent Beck", "978-0321146533");
        controller.addBook(book);
        
        List<Book> books1 = controller.getAllBooks();
        List<Book> books2 = controller.getAllBooks();
        
        assert books1 != books2 : "getAllBooks deve retornar uma nova lista";
        assert books1.size() == books2.size() : "As listas devem ter o mesmo tamanho";
        
        System.out.println("  (V) getAllBooks retorna uma nova lista (encapsulamento correto)\n");
    }

    // Teste: buscar livro por ISBN (encontrado)
    public void testFindBookByIsbn() {
        beforeEach();
        System.out.println("Teste: testFindBookByIsbn");
        
        Book book1 = new Book("Refactoring", "Martin Fowler", "978-0201485677");
        Book book2 = new Book("Code Complete", "Steve McConnell", "978-0735619678");
        
        controller.addBook(book1);
        controller.addBook(book2);
        
        Book found = controller.findBookByIsbn("978-0735619678");
        
        assert found != null : "Livro deveria ter sido encontrado";
        assert found.getTitle().equals("Code Complete") : "Livro incorreto encontrado";
        assert found.getAuthor().equals("Steve McConnell") : "Autor incorreto";
        
        System.out.println("  (V) Livro encontrado por ISBN com sucesso\n");
    }

    public void testFindBookByIsbnNotFound() {
        beforeEach();
        System.out.println("Teste: testFindBookByIsbnNotFound");
        
        Book book = new Book("The Pragmatic Programmer", "Andrew Hunt", "978-0201616224");
        controller.addBook(book);
        
        Book found = controller.findBookByIsbn("978-9999999999");
        
        assert found == null : "Nenhum livro deveria ter sido encontrado";
        
        System.out.println("  (V) Retornou null para ISBN não encontrado\n");
    }

    public void testRemoveBookByIsbn() {
        beforeEach();
        System.out.println("Teste: testRemoveBookByIsbn");
        
        Book book = new Book("Domain-Driven Design", "Eric Evans", "978-0321125217");
        controller.addBook(book);
        
        boolean removed = controller.removeBookByIsbn("978-0321125217");
        
        assert removed == true : "Livro deveria ter sido removido";
        assert controller.getAllBooks().size() == 0 : "Lista deveria estar vazia";
        
        System.out.println("  (V) Livro removido por ISBN com sucesso\n");
    }

    public void testRemoveBookByIsbnNotFound() {
        beforeEach();
        System.out.println("Teste: testRemoveBookByIsbnNotFound");
        
        Book book = new Book("Microservices", "Sam Newman", "978-1491950357");
        controller.addBook(book);
        
        boolean removed = controller.removeBookByIsbn("978-0000000000");
        
        assert removed == false : "Remoção deveria ter falhado";
        assert controller.getAllBooks().size() == 1 : "Livro não deveria ter sido removido";
        
        System.out.println(" (V) Retornou false para ISBN não encontrado na remoção\n");
    }

    public void testAddMultipleBooks() {
        beforeEach();
        System.out.println("Teste: testAddMultipleBooks");
        
        for (int i = 1; i <= 5; i++) {
            Book book = new Book("Livro " + i, "Autor " + i, "ISBN-" + i);
            controller.addBook(book);
        }
        
        List<Book> books = controller.getAllBooks();
        assert books.size() == 5 : "Esperado 5 livros, mas encontrado " + books.size();
        
        System.out.println("  (V) Múltiplos livros adicionados com sucesso\n");
    }

    public void testRemoveFromMultipleBooks() {
        beforeEach();
        System.out.println("Teste: testRemoveFromMultipleBooks");
        
        Book book1 = new Book("Livro A", "Autor A", "ISBN-A");
        Book book2 = new Book("Livro B", "Autor B", "ISBN-B");
        Book book3 = new Book("Livro C", "Autor C", "ISBN-C");
        
        controller.addBook(book1);
        controller.addBook(book2);
        controller.addBook(book3);
        
        boolean removed = controller.removeBookByIsbn("ISBN-B");
        
        assert removed == true : "Livro deveria ter sido removido";
        assert controller.getAllBooks().size() == 2 : "Esperado 2 livros restantes";
        assert controller.findBookByIsbn("ISBN-B") == null : "Livro B não deveria mais existir";
        assert controller.findBookByIsbn("ISBN-A") != null : "Livro A deveria ainda existir";
        assert controller.findBookByIsbn("ISBN-C") != null : "Livro C deveria ainda existir";
        
        System.out.println("  (V) Livro removido corretamente de uma lista com múltiplos livros\n");
    }
}
