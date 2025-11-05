package main.model;

import java.time.LocalDate;

public class Loan {
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    //Substituir esse primeiro if por uma funcao de validacao
    public Loan(Book book, User user, LocalDate loanDate, LocalDate dueDate) {
        if (book == null || user == null || loanDate == null || dueDate == null) {
            throw new IllegalArgumentException("Livro, Usuario, Data de emprestimo e Data de devolução não podem ser nulos.");
        }
        if (dueDate.isBefore(loanDate)){
            throw new IllegalArgumentException("A data de devolução não pode ser antes da data de emprestimo");
        }
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null;
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

    //Modificar essa parte do codigo devido a identacao ou modificar a identacao nas funcoes de outras classes
    public Book getBook() { return book; }
    public User getUser() { return user; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    @Override
    public String toString() {
        return "Livro: " + book.getTitle()
                + " | Usuário: " + user.getName()
                + " | Retirado: " + loanDate
                + " | Prazo: " + dueDate
                + (isReturned() ? " | Devolvido: " + returnDate : " | (em aberto)");
    }
}

