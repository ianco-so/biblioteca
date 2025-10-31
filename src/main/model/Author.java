package main.model;

public class Author {
    private String name;
    private String nationality;

    public Author (String name, String nationality){
        if (name == null || nationality == null){
            throw new IllegalArgumentException("Preencha os dados do autor corretamente");
        }
        this.name = name;
        this.nationality = nationality;
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
