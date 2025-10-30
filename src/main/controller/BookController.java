package main.controller;

import java.util.ArrayList;
import java.util.List;
import main.model.Book;

public class BookController {
    private List<Book> books = new ArrayList<>(); // TODO: talvez seja melhor usar outra estrutura de dados

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("O livro não pode ser nulo.");
        }
        this.books.add(book);
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
