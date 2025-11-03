package main.controller;

import main.model.Author;
import main.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private final List<Book> books = new ArrayList<>();


    public Book addBook(String title, String isbn, String authorName, String nationality, int numberOfCopies, boolean digitalAvailability) {
        // Validação básica
        if (title == null || isbn == null || authorName == null || nationality == null) {
            throw new IllegalArgumentException("Título, ISBN, nome e nacionalidade do autor são obrigatórios.");
        }

        title = title.trim();
        isbn = isbn.trim();
        authorName = authorName.trim();
        nationality = nationality.trim();

        Author author = new Author(authorName, nationality);
        Book book = new Book(title, author, isbn, numberOfCopies, digitalAvailability);

        books.add(book);

        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(this.books);
    }

    /**
     * Procura um livro pelo ISBN. (Retorna null se não encontrar)
     * @param isbn
     * @return Livro encontrado ou null se não encontrar
     */
    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public boolean verifyBookExist(String isbn){
        if(findBookByIsbn(isbn) != null){
            return true;
        }
        return false;
    }

    /**
     * Remove um livro pelo ISBN.
     * @param isbn
     * @return true se o livro foi removido, false se não foi encontrado
     */
    public boolean removeBookByIsbn(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book != null) {
            books.remove(book);
            return true;
        }
        return false;
    }
}
