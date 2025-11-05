package main.controller;

import main.model.Loan;
import main.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LoanController {
    private BookController bookController;
    private UserController userController;
    private List<Loan> loans = new ArrayList<>();

    public LoanController(BookController bookController, UserController userController) {
        this.bookController = bookController;
        this.userController = userController;
    }

    public boolean loanPhysical(String userId, String isbn) {
        User user = userController.findUser(userId);
        var bookOpt = bookController.findByIsbn(isbn);
        if (user == null || bookOpt.isEmpty()) {
            System.out.println("Usuário ou livro não encontrados.");
            return false;
        }
        var book = bookOpt.get();
        if (book.getNumberOfCopies() <= 0) {
            System.out.println("Sem cópias físicas disponíveis.");
            return false;
        }

        LocalDate now = LocalDate.now();
        Loan loan = new Loan(book, user, now, now.plusDays(14));
        loans.add(loan);

        book.decrementCopies();

        System.out.println("Empréstimo FÍSICO criado com sucesso!");
        return true;
    }

    public boolean loanDigital(String userId, String isbn) {
        User user = userController.findUser(userId);
        var bookOpt = bookController.findByIsbn(isbn);
        if (user == null || bookOpt.isEmpty()) {
            System.out.println("Usuário ou livro não encontrados.");
            return false;
        }
        var book = bookOpt.get();
        if (!book.getDigitalAvailability()) {
            System.out.println("Este livro não possui versão digital disponível.");
            return false;
        }

        LocalDate now = LocalDate.now();
        Loan loan = new Loan(book, user, now, now.plusDays(14));
        loans.add(loan);

        System.out.println("Empréstimo DIGITAL criado com sucesso!");
        return true;
    }

    public boolean returnBook(String userId, String isbn) {
        User user = userController.findUser(userId);
        var bookOpt = bookController.findByIsbn(isbn);
        if (user == null || bookOpt.isEmpty()) {
            System.out.println("Usuário ou livro não encontrados.");
            return false;
        }
        var book = bookOpt.get();

        Loan open = null;
        for (Loan l : loans) {
            if (!l.isReturned() && l.getUser().equals(user) && l.getBook().equals(book)) {
                open = l;
                break;
            }
        }
        if (open == null) {
            System.out.println("Não há empréstimo aberto para esse usuário e livro.");
            return false;
        }

        open.returnNow();

        if (!open.getBook().getDigitalAvailability()) {
            open.getBook().incrementCopies();
        }

        System.out.println("Livro devolvido com sucesso!");
        return true;
    }

    public boolean extendDueDate(String userId, String isbn, LocalDate newDate) {
        User user = userController.findUser(userId);
        var bookOpt = bookController.findByIsbn(isbn);
        if (user == null || bookOpt.isEmpty()) {
            System.out.println("Usuário ou livro não encontrados.");
            return false;
        }
        var book = bookOpt.get();

        Loan open = null;
        for (Loan l : loans) {
            if (!l.isReturned() && l.getUser().equals(user) && l.getBook().equals(book)) {
                open = l;
                break;
            }
        }
        if (open == null) {
            System.out.println("Não há empréstimo aberto para esse usuário e livro.");
            return false;
        }

        try {
            open.setDueDate(newDate);
            System.out.println("Prazo estendido para " + newDate + ".");
            return true;
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Falha ao estender prazo: " + e.getMessage());
            return false;
        }
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    public List<Loan> listOpenLoans() {
        List<Loan> result = new ArrayList<>();
        for (Loan l : loans) {
            if (!l.isReturned()) result.add(l);
        }
        return result;
    }

    public List<Loan> listClosedLoans() {
        List<Loan> result = new ArrayList<>();
        for (Loan l : loans) {
            if (l.isReturned()) result.add(l);
        }
        return result;
    }

    public List<Loan> listLoansSortedByLoanDateDesc() {
        List<Loan> result = getAllLoans();
        result.sort(Comparator.comparing(Loan::getLoanDate).reversed());
        return result;
    }
}
