package main.java.model;

public class Author {
    private String name;
    private String nationality;

    public Author (String name, String nationality) {
        validate(name, nationality);
        this.name = name;
        this.nationality = nationality;
    }

    private static void validate(String name, String nationality) {
        if (name == null || nationality == null) {
            throw new IllegalArgumentException("Nome e nacionalidade não podem ser nulos.");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (nationality.trim().isEmpty()) {
            throw new IllegalArgumentException("Nacionalidade não pode ser vazia.");
        }
        if (name.trim().length() < 2) {
            throw new IllegalArgumentException("Nome muito curto.");
        }
        if (nationality.trim().length() < 2) {
            throw new IllegalArgumentException("Nacionalidade muito curta.");
        }
    }

    public String getNationality() {
        return nationality;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + nationality + ")";
    }
}
