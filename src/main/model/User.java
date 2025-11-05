package main.model;

public class User {
    private String name;
    private String id;
    private String loanHistory;//Talvez trocar essa string por uma lista de Loans

    public User(String name, String id){
        this.name = name;
        this.id = id;
        this.loanHistory = null; //TODO: adicionar historico previamente ou fazer em tempo de execucao
    }

    public String getName(){
        return name;
    }

    public String getID(){
        return id;
    }

    public String getLoanHistory(){
        return loanHistory;
    }

}
