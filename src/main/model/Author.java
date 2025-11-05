package main.model;


//No meu entendimento a classe Author nao parece realmente necessaria para o trabalho, uma vez que os
// requisitos, da minha perspectiva, nao parecem requer sua criacao,
//mas ela também ja ta aqui.
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
