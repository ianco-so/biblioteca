package main.controller;

import main.model.Book;
import main.model.Loan;
import main.model.User;
import main.model.enums.LoanFilter;

import java.lang.foreign.Linker.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LoanController {
    private BookController bookController;
    private UserController userController;
    private List<Loan> loans = new ArrayList<>();

    public LoanController(BookController bookController, UserController userController) {
        this.bookController = bookController;
        this.userController = userController;
    }

    private record UserAndBook(User user, Book book) {}

    private UserAndBook findUserAndBookOrThrow(String userId, String isbn) {
        var userOpt = userController.findById(userId);
        var bookOpt = bookController.findByIsbn(isbn);
        
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        if (bookOpt.isEmpty()) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        return new UserAndBook(userOpt.get(), bookOpt.get());
    }

    public Loan loan(String userId, String isbn, boolean isDigital) {
        var ub = findUserAndBookOrThrow(userId, isbn); 
        
        if (isDigital) {
            if (ub.book().getNumberOfCopies() <= 0) {
                throw new IllegalStateException("Sem cópias disponíveis.");
            }
        } else {
            if (!ub.book().getDigitalAvailability()) {
                throw new IllegalStateException("Este livro não possui versão digital disponível.");
            }
            ub.book().decrementCopies();
        }
            
        var loan = new Loan(ub.book(), ub.user());
        loans.add(loan);
        return loan;
    }

    public boolean returnBook(String userId, String isbn) {
        var userOpt = userController.findById(userId);
        var bookOpt = bookController.findByIsbn(isbn);
        if (userOpt.isEmpty() || bookOpt.isEmpty()) {
            System.out.println("Usuário ou livro não encontrados.");
            return false;
        }
        var book = bookOpt.get();
        var user = userOpt.get();
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
        var userOpt = userController.findById(userId);
        var bookOpt = bookController.findByIsbn(isbn);
        if (userOpt.isPresent() || bookOpt.isEmpty()) {
            System.out.println("Usuário ou livro não encontrados.");
            return false;
        }
        var book = bookOpt.get();
        var user = userOpt.get();
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

    public List<Loan> getLoansWithFilter (LoanFilter state) {
        List<Loan> result = new ArrayList<>();
        if (state == LoanFilter.ALL) {
            return getAllLoans();
        }
        if (state == LoanFilter.OPEN) {
            for (Loan l : loans) {
                if (!l.isReturned()) result.add(l);
            }
        } else if (state == LoanFilter.CLOSED) {
            for (Loan l : loans) {
                if (l.isReturned()) result.add(l);
            }
        }
        return result;
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    // public List<Loan> listOpenLoans() {
    //     List<Loan> result = new ArrayList<>();
    //     for (Loan l : loans) {
    //         if (!l.isReturned()) result.add(l);
    //     }
    //     return result;
    // }

    // public List<Loan> listClosedLoans() {
    //     List<Loan> result = new ArrayList<>();
    //     for (Loan l : loans) {
    //         if (l.isReturned()) result.add(l);
    //     }
    //     return result;
    // }

    public List<Loan> listLoansSortedByLoanDateDesc() {
        List<Loan> result = getAllLoans();
        result.sort(Comparator.comparing(Loan::getLoanDate).reversed());
        return result;
    }
}
