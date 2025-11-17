package main.java.controller;

import main.java.model.Author;
import main.java.model.Book;
import main.java.util.IsbnValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookController {
    private final List<Book> books = new ArrayList<>();

    /**
     * Adiciona um novo livro. Caso o ISBN já exista, não adiciona novamente.
     * @param title
     * @param isbn
     * @param authorName
     * @param nationality
     * @param numberOfCopies
     * @param digitalAvailability
     * @return Livro adicionado ou existente
     * @throws IllegalArgumentException se algum dos parâmetros obrigatórios estiver ausente
     */
    public Book addBook(String title, 
                        String isbn, 
                        String authorName, 
                        String nationality, 
                        int numberOfCopies, 
                        boolean digitalAvailability) { // TODO: Refatorar muitos parâmetros

        var author = new Author(authorName, nationality);
        var book = new Book(title, author, isbn, numberOfCopies, digitalAvailability);

        if (!this.hasBook(isbn)) {
            books.add(book);
        } else {
            throw new IllegalStateException("Livro com ISBN '" + isbn + "' já existe.");
        }

        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(this.books);
    }

    /**
     * Procura um livro pelo ISBN. (Retorna null se não encontrar)
     * @param isbn
     * @return Livro encontrado ou null se não encontrar
     * @throws NullPointerException
     */
    public Optional<Book> findByIsbn(String isbn) {
        return this.books.stream()
                        .filter(book -> book.getIsbn().equals(IsbnValidator.getCleanIsbn(isbn)))
                        .findFirst();
    }

    /**
     * Verifica se um livro existe pelo ISBN.
     * @param isbn
     * @return true se o livro existir, false caso contrário
     */
    public boolean hasBook(String isbn){
        return this.books.stream().anyMatch(book -> book.getIsbn().equals(IsbnValidator.getCleanIsbn(isbn)));
    }

    /**
     * Remove um livro pelo ISBN.
     * @param isbn
     * @return true se o livro foi removido, false se não foi encontrado
     */
    public boolean removeByIsbn(String isbn) {
        return books.removeIf(book -> book.getIsbn().equals(IsbnValidator.getCleanIsbn(isbn)));
    }
}
