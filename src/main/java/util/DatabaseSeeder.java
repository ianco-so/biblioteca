package util;

import controller.BookController;
import controller.LoanController;
import controller.UserController;

public class DatabaseSeeder {
    
    private final BookController bookController;
    private final UserController userController;
    private final LoanController loanController;

    public DatabaseSeeder(BookController bookController, 
                         UserController userController, 
                         LoanController loanController
    ) {
        this.bookController = bookController;
        this.userController = userController;
        this.loanController = loanController;
    }

    public void seed() {
        seedBooks();
        seedUsers();
        seedLoans();     
    }

    private void seedBooks() {
        try {
            bookController.addBook(
                "Clean Code",
                "0-7499-0514-X",
                "Robert C. Martin",
                "Estados Unidos da America",
                5,
                true
            ); 
            bookController.addBook(
                "Design Patterns",
                "978-9-4835-4632-8",
                "Erich Gamma",
                "Suíça",
                4,
                false
            ); 
            bookController.addBook(
                "Refactoring",
                "0-6104-0068-1",
                "Martin Fowler",
                "Inglaterra",
                6,
                true
            );      
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao carregar livros: " + e.getMessage());
            System.out.println();
        }    
    }

    private void seedUsers() {
        try {
            userController.registerUser("João Silva", "12345678901");  
            userController.registerUser("Maria Santos", "98765432100");
            userController.registerUser("Pedro Oliveira", "11122233344");       
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
            System.out.println();
        }
    }

    private void seedLoans() {
        try {
            // Empréstimos para João Silva
            loanController.loan("12345678901", "0-7499-0514-X", true); 
            loanController.loan("12345678901", "978-9-4835-4632-8", false); 
            loanController.loan("12345678901", "0-6104-0068-1", true);
            
            // Empréstimos para Maria Santos
            loanController.loan("98765432100", "0-7499-0514-X", true);
            loanController.loan("98765432100", "978-9-4835-4632-8", false); 
            loanController.loan("98765432100", "0-6104-0068-1", true);
            
            // Empréstimos para Pedro Oliveira
            loanController.loan("11122233344", "0-7499-0514-X", true);
            loanController.loan("11122233344", "978-9-4835-4632-8", false);
            loanController.loan("11122233344", "0-6104-0068-1", true);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Erro ao carregar empréstimos: " + e.getMessage());
            System.out.println();
        }
    }
}