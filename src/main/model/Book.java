package main.model;

public class Book {
    private String title;
    private Author author;
    private String isbn;
    private int numberOfCopies;
    private boolean digitalAvailability;
    // private Publisher publisher; //TODO: Implementar Publisher


    public Book(String title, Author author, String isbn, int numberOfCopies, boolean digitalAvailability) {
        if (title == null || author == null || isbn == null) { // Validação simples // TODO: retirar e usar Lombok
            throw new IllegalArgumentException("Titulo, autor e ISBN não podem ser nulos.");
        }
        this.title = title.trim();
        this.author = author;
        this.isbn = isbn.trim(); //TODO: Validar ISBN
        this.numberOfCopies = numberOfCopies; //TODO: validar o int do numero de copias
        this.digitalAvailability = digitalAvailability;
    }

    public String getTitle() { //TODO: Retirar. Vamos tentar usar o Lombok
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
}