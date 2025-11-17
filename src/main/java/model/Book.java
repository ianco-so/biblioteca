package main.java.model;

import main.java.util.IsbnValidator;

public class Book {
    private String title;
    private Author author;
    private String isbn;
    private int numberOfCopies;
    private boolean digitalAvailability;


    public Book(String title, Author author, String isbn, int numberOfCopies, boolean digitalAvailability) {
        validate(title, author, isbn, numberOfCopies);
        this.title = title.trim();
        this.author = author;
        this.isbn = IsbnValidator.getCleanIsbn(isbn);
        this.numberOfCopies = numberOfCopies;
        this.digitalAvailability = digitalAvailability;
    }

    public Book(String title, Author author, String isbn) {
        this(title, author, isbn, 0, false);
    }

    private static void validate (String title, Author author, String isbn, int numberOfCopies) {
        if (title == null || author == null || isbn == null) {
            throw new IllegalArgumentException("Titulo, autor e ISBN não podem ser nulos.");
        }
        if (title.trim().isEmpty()) {
            throw new IllegalArgumentException("Titulo não pode ser vazio.");
        }
        if (isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN não pode ser vazio.");
        }
        if (numberOfCopies < 0) {
            throw new IllegalArgumentException("Número de cópias não pode ser negativo.");
        }
        if (!IsbnValidator.isValid(isbn)) {
            throw new IllegalArgumentException("ISBN inválido.");
        }
    }

    public void decrementCopies() { //TODO: Será que é necessário lançar uma exceção aqui?
        if (numberOfCopies <= 0) {
            throw new IllegalStateException("Sem cópias físicas disponíveis.");
        }
        numberOfCopies--;
    }

    public void incrementCopies() {
        numberOfCopies++;
    }

    public String getTitle() {
        return this.title;
    }

    public Author getAuthor() {
        return this.author;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public int getNumberOfCopies(){
        return this.numberOfCopies;
    }

    public boolean getDigitalAvailability(){
        return this.digitalAvailability;
    }

    @Override
    public String toString() {
        return title + " | " + author + " | " + isbn;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book other = (Book) obj;
        return isbn.equals(other.isbn);
    }
}