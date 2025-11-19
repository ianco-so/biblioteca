package view;

import controller.BookController;
import controller.LoanController;
import controller.UserController;

public class LoanViewTest {

    public static void main(String[] args) {
        LoanViewTest test = new LoanViewTest();
        
        System.out.println("=== Testes do LoanView ===");
        
        System.out.println("\n\t\tTeste básico");
        test.testLoanControllerIntegration();
        
        System.out.println("\n==========================");
    }

    private void printSuccess() {
        System.out.println("success!");
    }

    public void testLoanControllerIntegration() {
        System.out.print("testLoanControllerIntegration: ");
        
        BookController bookController = new BookController();
        UserController userController = new UserController();
        
        bookController.addBook(
            "Clean Code",
            "978-0132350884",
            "Robert Martin",
            "Americana",
            5,
            true
        );
        
        userController.registerUser("João Silva", "user123");
        
        LoanController loanController = new LoanController(bookController, userController);
        
        var loan = loanController.loan("user123", "978-0132350884", false);
        
        assert loan != null : "Empréstimo deveria ter sido criado";
        assert !loan.isReturned() : "Empréstimo deveria estar aberto";
        assert loanController.getAllLoans().size() == 1 : "Deveria ter 1 empréstimo";
        
        printSuccess();
    }
}
