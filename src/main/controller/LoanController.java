package main.controller;

import main.model.Book;
import main.model.Loan;
import main.model.User;
import main.model.enums.LoanFilter;

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

        if (!isDigital) {
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
        ub.user().addLoanToHistory(loan);
        loans.add(loan);
        return loan;
    }

    public Optional<Loan> returnLoanedBook(String userId, String isbn) {
        var openLoan = findOpenLoan(userId, isbn);
        
        if (openLoan.isEmpty()) {
            throw new IllegalStateException("Não há empréstimo aberto para esse usuário e livro.");
        }

        try {
            openLoan.get().returnNow();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Falha ao devolver livro: " + e.getMessage());
        }
        
        var loanedBook = openLoan.get().getBook();
        loanedBook.incrementCopies();

        return openLoan;
    }

    public Optional<Loan> extendDueDate(String userId, String isbn, LocalDate newDate) {
        var openLoan = findOpenLoan(userId, isbn);
        
        if (openLoan.isEmpty()) {
            throw new IllegalStateException("Não há empréstimo aberto para esse usuário e livro.");
        }

        try {
            openLoan.get().setDueDate(newDate);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new IllegalStateException("Falha ao estender prazo: " + e.getMessage());
        }
        
        return openLoan;
    }

    private Optional<Loan> findOpenLoan(String userId, String isbn) {
        var ub = findUserAndBookOrThrow(userId, isbn);
        
        return this.loans.stream()
            .filter(loan -> !loan.isReturned())
            .filter(loan -> loan.getUser().equals(ub.user()))
            .filter(loan -> loan.getBook().equals(ub.book()))
            .findFirst();
    }   

    public List<Loan> getLoansWithFilter (LoanFilter filter) {
        List<Loan> result = new ArrayList<>();
        if (filter == LoanFilter.ALL) {
            return getAllLoans();
        }
        if (filter == LoanFilter.OPEN) {
            for (Loan l : loans) {
                if (!l.isReturned()) result.add(l);
            }
        } else if (filter == LoanFilter.CLOSED) {
            for (Loan l : loans) {
                if (l.isReturned()) result.add(l);
            }
        }
        return result;
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    public List<Loan> listLoansSortedByLoanDateDesc() {
        List<Loan> result = getAllLoans();
        result.sort(Comparator.comparing(Loan::getLoanDate).reversed());
        return result;
    }
}
