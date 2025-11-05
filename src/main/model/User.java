package main.model;

import java.util.List;

public class User {
    private String name;
    private String id;
    private List<Loan> loanHistory;


    public User(String name, String id) {
        validate(name, id);
        this.name = name;
        this.id = id; 
        this.loanHistory = List.of();
    }

    private static void validate(String name, String id) {
        if (name == null || id == null) {
            throw new IllegalArgumentException("Nome e ID não podem ser nulos.");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser vazio.");
        }
        if (name.trim().length() < 3) {
            throw new IllegalArgumentException("Nome muito curto.");
        }
        if (id.trim().length() < 3) {
            throw new IllegalArgumentException("ID muito curto.");
        }
        if (id.trim().length() > 20) {
            throw new IllegalArgumentException("ID muito longo.");
        }
        if (!id.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("ID deve ser alfanumérico.");
        }
    }    

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

    public List<Loan> getLoanHistory() {
        return List.copyOf(loanHistory);
    }

    public void addLoanToHistory(Loan loan) {
        this.loanHistory.add(loan);
    }
}
