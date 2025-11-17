package main.java.model;

import java.time.LocalDate;

public class Loan {
    private static final int DEFAULT_LOAN_PERIOD_DAYS = 14;
    private static final int MAX_LOAN_PERIOD_DAYS = 60;

    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // null se não devolvido ainda
    private boolean isDigital;

    public Loan(Book book, User user, LocalDate loanDate, LocalDate dueDate, boolean isDigital) {
        validate(book, user, loanDate, dueDate);
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.isDigital = isDigital;
    }   

    public Loan(Book book, User user, boolean isDigital) {
        this(book, user, LocalDate.now(), LocalDate.now().plusDays(DEFAULT_LOAN_PERIOD_DAYS), isDigital);
    }

    private static void validate (Book book, User user, LocalDate loanDate, LocalDate dueDate) {
        if (book == null || user == null || loanDate == null || dueDate == null) {
            throw new IllegalArgumentException("Livro, Usuario, Data de emprestimo e Data de devolução não podem ser nulos.");
        }
        if (dueDate.isBefore(loanDate)){
            throw new IllegalArgumentException("A data de devolução não pode ser antes da data de emprestimo");
        }
        if (dueDate.isAfter(loanDate.plusDays(MAX_LOAN_PERIOD_DAYS))) {
            throw new IllegalArgumentException("O período máximo de empréstimo é de " + MAX_LOAN_PERIOD_DAYS + " dias.");
        }
    }

    public void returnNow(){
        if (isReturned()) {
            throw new IllegalStateException("Empréstimo já devolvido");
        }
        this.returnDate = LocalDate.now();
    }

    public void setDueDate(LocalDate date){
        if (date == null) {
            throw new IllegalArgumentException("A data não pode ser nula");
        }
        if (isReturned()) {
            throw new IllegalStateException("Empréstimo já devolvido");
        }
        if (date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("A devolução não pode ser antes da data atual");
        }
        this.dueDate = date;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public Book getBook() { 
        return book; 
    }
    public User getUser() { 
        return user; 
    }
    public LocalDate getLoanDate() { 
        return loanDate; 
    }
    public LocalDate getDueDate() { 
        return dueDate; 
    }
    public LocalDate getReturnDate() { 
        return returnDate; 
    }
    public boolean isDigital() {
        return isDigital;
    }

    @Override
    public String toString() {
        return "Livro: " + book.getTitle()
                + " | Usuário: " + user.getName()
                + " | Retirado: " + loanDate
                + " | Prazo: " + dueDate
                + (isReturned() ? " | Devolvido: " + returnDate : " | (em aberto)");
    }
}

