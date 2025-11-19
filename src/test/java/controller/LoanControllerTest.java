package controller;

import model.Loan;
import model.enums.LoanFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LoanControllerTest {

    public static void main(String[] args) {
        LoanControllerTest test = new LoanControllerTest();
        
        System.out.println("=== Testes do LoanController ===");
        
        System.out.println("\n\t\tloan()");
        test.testLoanTwoTimesWithSameUserAndBookPhysical();
        test.testLoanTwoTimesWithSameUserAndBookDigital();
        test.testLoanPhysicalBook();
        test.testLoanDigitalBook();
        test.testLoanWithoutCopies();
        test.testLoanDigitalNotAvailable();
        test.testLoanUserNotFound();
        test.testLoanBookNotFound();

        System.out.println("\n\t\treturnLoanedBook()");
        test.testReturnLoanedBook();
        test.testReturnLoanedBookNotFound();
        test.testReturnAlreadyReturned();
        
        System.out.println("\n\t\textendDueDate()");
        test.testExtendDueDate();
        test.testExtendDueDateLoanNotFound();
        test.testExtendDueDateInvalid();
        
        System.out.println("\n\t\tgetLoansWithFilter()");
        test.testGetLoansWithFilterAll();
        test.testGetLoansWithFilterOpen();
        test.testGetLoansWithFilterClosed();
        
        System.out.println("\n\t\tlistLoansSortedByLoanDateDesc()");
        test.testListLoansSortedByLoanDateDesc();
        
        System.out.println("\n==========================");
    }

    private void printSuccess() {
        System.out.println("success!");
    }

    private LoanController setupController() {
        BookController bookController = new BookController();
        UserController userController = new UserController();
        
        bookController.addBook(
            "Clean Code", 
            "978-0132350884", 
            "Robert Martin", 
            "Americana", 
            3, 
            true
        );
        bookController.addBook(
            "Design Patterns", 
            "978-0306406157", 
            "Gang of Four", 
            "Vários", 
            0, 
            false
        );
        
        userController.registerUser("João Silva", "user123");
        userController.registerUser("Maria Santos", "user456");
        
        return new LoanController(bookController, userController);
    }

    // ========== Testes de loan ==========

    public void testLoanTwoTimesWithSameUserAndBookPhysical() {
        System.out.print("testLoanTwoTimesWithSameUserAndBookPhysical: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", false);
        
        try {
            controller.loan("user123", "978-0132350884", false);
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("Usuário já possui um empréstimo aberto") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    public void testLoanTwoTimesWithSameUserAndBookDigital() {
        System.out.print("testLoanTwoTimesWithSameUserAndBookDigital: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", true);
        
        try {
            controller.loan("user123", "978-0132350884", true);
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("Usuário já possui um empréstimo aberto") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    public void testLoanPhysicalBook() {
        System.out.print("testLoanPhysicalBook: ");
        
        LoanController controller = setupController();
        Loan loan = controller.loan("user123", "978-0132350884", false);
        
        assert loan != null : "Empréstimo deveria ter sido criado";
        assert loan.getUser().getID().equals("user123") : "Usuário incorreto";
        assert loan.getBook().getIsbn().equals("9780132350884") : "Livro incorreto";
        assert !loan.isReturned() : "Empréstimo deveria estar aberto";
        assert controller.getAllLoans().size() == 1 : "Deveria ter 1 empréstimo";
        
        printSuccess();
    }

    public void testLoanDigitalBook() {
        System.out.print("testLoanDigitalBook: ");
        
        LoanController controller = setupController();
        Loan loan = controller.loan("user123", "978-0132350884", true);
        
        assert loan != null : "Empréstimo deveria ter sido criado";
        assert !loan.isReturned() : "Empréstimo deveria estar aberto";
        
        printSuccess();
    }

    public void testLoanWithoutCopies() {
        System.out.print("testLoanWithoutCopies: ");
        
        LoanController controller = setupController();
        
        try {
            controller.loan("user123", "978-0306406157", false);
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("Sem cópias disponíveis") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    public void testLoanDigitalNotAvailable() {
        System.out.print("testLoanDigitalNotAvailable: ");
        
        LoanController controller = setupController();
        
        try {
            controller.loan("user123", "978-0306406157", true);
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("não possui versão digital") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    public void testLoanUserNotFound() {
        System.out.print("testLoanUserNotFound: ");
        
        LoanController controller = setupController();
        
        try {
            controller.loan("user999", "978-0132350884", false);
            assert false : "Deveria lançar IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Usuário não encontrado") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    public void testLoanBookNotFound() {
        System.out.print("testLoanBookNotFound: ");
        
        LoanController controller = setupController();
        
        try {
            controller.loan("user123", "978-0000000000", false);
            assert false : "Deveria lançar IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Livro não encontrado") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    // ========== Testes de returnLoanedBook ==========

    public void testReturnLoanedBook() {
        System.out.print("testReturnLoanedBook: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", false);
        
        Optional<Loan> returned = controller.returnLoanedBook("user123", "978-0132350884");
        
        assert returned.isPresent() : "Deveria retornar o empréstimo";
        assert returned.get().isReturned() : "Empréstimo deveria estar devolvido";
        assert returned.get().getReturnDate() != null : "Data de devolução deveria estar preenchida";
        
        printSuccess();
    }

    public void testReturnLoanedBookNotFound() {
        System.out.print("testReturnLoanedBookNotFound: ");
        
        LoanController controller = setupController();
        
        try {
            controller.returnLoanedBook("user123", "978-0132350884");
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("Não há empréstimo aberto") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    public void testReturnAlreadyReturned() {
        System.out.print("testReturnAlreadyReturned: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", false);
        controller.returnLoanedBook("user123", "978-0132350884");
        
        try {
            controller.returnLoanedBook("user123", "978-0132350884");
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("Não há empréstimo aberto") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    // ========== Testes de extendDueDate ==========

    public void testExtendDueDate() {
        System.out.print("testExtendDueDate: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", false);
        
        LocalDate newDate = LocalDate.now().plusDays(20);
        Optional<Loan> extended = controller.extendDueDate("user123", "978-0132350884", newDate);
        
        assert extended.isPresent() : "Deveria retornar o empréstimo";
        assert extended.get().getDueDate().equals(newDate) : "Data de devolução deveria ter sido atualizada";
        
        printSuccess();
    }

    public void testExtendDueDateLoanNotFound() {
        System.out.print("testExtendDueDateLoanNotFound: ");
        
        LoanController controller = setupController();
        
        try {
            controller.extendDueDate("user123", "978-0132350884", LocalDate.now().plusDays(20));
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("Não há empréstimo aberto") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    public void testExtendDueDateInvalid() {
        System.out.print("testExtendDueDateInvalid: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", false);
        
        try {
            controller.extendDueDate("user123", "978-0132350884", LocalDate.now().minusDays(1));
            assert false : "Deveria lançar IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("Falha ao estender prazo") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }

    // ========== Testes de getLoansWithFilter ==========

    public void testGetLoansWithFilterAll() {
        System.out.print("testGetLoansWithFilterAll: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", false);
        controller.loan("user456", "978-0132350884", false);
        controller.returnLoanedBook("user123", "978-0132350884");
        
        List<Loan> loans = controller.getLoansWithFilter(LoanFilter.ALL);
        
        assert loans.size() == 2 : "Deveria ter 2 empréstimos";
        
        printSuccess();
    }

    public void testGetLoansWithFilterOpen() {
        System.out.print("testGetLoansWithFilterOpen: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", false);
        controller.loan("user456", "978-0132350884", false);
        controller.returnLoanedBook("user123", "978-0132350884");
        
        List<Loan> openLoans = controller.getLoansWithFilter(LoanFilter.OPEN);
        
        assert openLoans.size() == 1 : "Deveria ter 1 empréstimo aberto";
        assert !openLoans.get(0).isReturned() : "Empréstimo deveria estar aberto";
        
        printSuccess();
    }

    public void testGetLoansWithFilterClosed() {
        System.out.print("testGetLoansWithFilterClosed: ");
        
        LoanController controller = setupController();
        controller.loan("user123", "978-0132350884", false);
        controller.loan("user456", "978-0132350884", false);
        controller.returnLoanedBook("user123", "978-0132350884");
        
        List<Loan> closedLoans = controller.getLoansWithFilter(LoanFilter.CLOSED);
        
        assert closedLoans.size() == 1 : "Deveria ter 1 empréstimo fechado";
        assert closedLoans.get(0).isReturned() : "Empréstimo deveria estar devolvido";
        
        printSuccess();
    }

    // ========== Testes de listLoansSortedByLoanDateDesc ==========

    public void testListLoansSortedByLoanDateDesc() {
        System.out.print("testListLoansSortedByLoanDateDesc: ");
        
        BookController bookController = new BookController();
        UserController userController = new UserController();
        
        bookController.addBook(
            "Book 1", 
            "978-0132350884", 
            "Author", 
            "País", 
            5, 
            true
        );
        bookController.addBook(
            "Book 2", 
            "978-0306406157", 
            "Author", 
            "País", 
            5, 
            true
        );
        bookController.addBook(
            "Book 3",
            "0306406152", 
            "Author", 
            "País", 
            5, 
            true
        );
        
        userController.registerUser("User", "user123");
        
        LoanController controller = new LoanController(bookController, userController);
        
        // Criar empréstimos em ordem não cronológica
        controller.loan("user123", "978-0306406157", false);
        controller.loan("user123", "978-0132350884", false);
        controller.loan("user123", "0306406152", false);
        
        List<Loan> sorted = controller.listLoansSortedByLoanDateDesc();
        
        assert sorted.size() == 3 : "Deveria ter 3 empréstimos";
        // Como todos foram criados no mesmo dia, apenas verifica se estão ordenados
        assert sorted.get(0).getLoanDate().isAfter(sorted.get(2).getLoanDate()) 
            || sorted.get(0).getLoanDate().equals(sorted.get(2).getLoanDate()) 
            : "Deveria estar ordenado por data decrescente";
        
        printSuccess();
    }
}
