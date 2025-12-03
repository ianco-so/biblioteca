package view;

import controller.BookController;
import controller.LoanController;
import controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do LoanView")
class LoanViewTest {

    @Test
    @DisplayName("Deve integrar com LoanController corretamente")
    void testLoanControllerIntegration() {
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
        
        assertNotNull(loan, "Empréstimo deveria ter sido criado");
        assertFalse(loan.isReturned(), "Empréstimo deveria estar aberto");
        assertEquals(1, loanController.getAllLoans().size(), "Deveria ter 1 empréstimo");
    }
}
