package controller;

import model.Loan;
import model.enums.LoanFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do LoanController")
class LoanControllerTest {

    private LoanController controller;
    private BookController bookController;
    private UserController userController;

    @BeforeEach
    void setUp() {
        bookController = new BookController();
        userController = new UserController();
        
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
        
        controller = new LoanController(bookController, userController);
    }

    // ========== Testes de loan ==========

    @Test
    @DisplayName("Deve lançar exceção ao emprestar mesmo livro físico duas vezes para o mesmo usuário")
    void testLoanTwoTimesWithSameUserAndBookPhysical() {
        controller.loan("user123", "978-0132350884", false);
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.loan("user123", "978-0132350884", false)
        );
        
        assertTrue(exception.getMessage().contains("Usuário já possui um empréstimo aberto"), 
            "Mensagem de erro incorreta");
    }

    @Test
    @DisplayName("Deve lançar exceção ao emprestar mesmo livro digital duas vezes para o mesmo usuário")
    void testLoanTwoTimesWithSameUserAndBookDigital() {
        controller.loan("user123", "978-0132350884", true);
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.loan("user123", "978-0132350884", true)
        );
        
        assertTrue(exception.getMessage().contains("Usuário já possui um empréstimo aberto"), 
            "Mensagem de erro incorreta");
    }

    @Test
    @DisplayName("Deve emprestar livro físico com sucesso")
    void testLoanPhysicalBook() {
        Loan loan = controller.loan("user123", "978-0132350884", false);
        
        assertNotNull(loan, "Empréstimo deveria ter sido criado");
        assertEquals("user123", loan.getUser().getID(), "Usuário incorreto");
        assertEquals("9780132350884", loan.getBook().getIsbn(), "Livro incorreto");
        assertFalse(loan.isReturned(), "Empréstimo deveria estar aberto");
        assertEquals(1, controller.getAllLoans().size(), "Deveria ter 1 empréstimo");
    }

    @Test
    @DisplayName("Deve emprestar livro digital com sucesso")
    void testLoanDigitalBook() {
        Loan loan = controller.loan("user123", "978-0132350884", true);
        
        assertNotNull(loan, "Empréstimo deveria ter sido criado");
        assertFalse(loan.isReturned(), "Empréstimo deveria estar aberto");
    }

    @Test
    @DisplayName("Deve lançar exceção ao emprestar livro sem cópias disponíveis")
    void testLoanWithoutCopies() {
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.loan("user123", "978-0306406157", false)
        );
        
        assertTrue(exception.getMessage().contains("Sem cópias disponíveis"), "Mensagem de erro incorreta");
    }

    @Test
    @DisplayName("Deve lançar exceção ao emprestar livro digital não disponível")
    void testLoanDigitalNotAvailable() {
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.loan("user123", "978-0306406157", true)
        );
        
        assertTrue(exception.getMessage().contains("não possui versão digital"), "Mensagem de erro incorreta");
    }

    @Test
    @DisplayName("Deve lançar exceção ao emprestar para usuário inexistente")
    void testLoanUserNotFound() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> controller.loan("user999", "978-0132350884", false)
        );
        
        assertTrue(exception.getMessage().contains("Usuário não encontrado"), "Mensagem de erro incorreta");
    }

    @Test
    @DisplayName("Deve lançar exceção ao emprestar livro inexistente")
    void testLoanBookNotFound() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> controller.loan("user123", "978-0000000000", false)
        );
        
        assertTrue(exception.getMessage().contains("Livro não encontrado"), "Mensagem de erro incorreta");
    }

    // ========== Testes de returnLoanedBook ==========

    @Test
    @DisplayName("Deve devolver livro emprestado com sucesso")
    void testReturnLoanedBook() {
        controller.loan("user123", "978-0132350884", false);
        
        Optional<Loan> returned = controller.returnLoanedBook("user123", "978-0132350884");
        
        assertTrue(returned.isPresent(), "Deveria retornar o empréstimo");
        assertTrue(returned.get().isReturned(), "Empréstimo deveria estar devolvido");
        assertNotNull(returned.get().getReturnDate(), "Data de devolução deveria estar preenchida");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar devolver livro não emprestado")
    void testReturnLoanedBookNotFound() {
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.returnLoanedBook("user123", "978-0132350884")
        );
        
        assertTrue(exception.getMessage().contains("Não há empréstimo aberto"), "Mensagem de erro incorreta");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar devolver livro já devolvido")
    void testReturnAlreadyReturned() {
        controller.loan("user123", "978-0132350884", false);
        controller.returnLoanedBook("user123", "978-0132350884");
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.returnLoanedBook("user123", "978-0132350884")
        );
        
        assertTrue(exception.getMessage().contains("Não há empréstimo aberto"), "Mensagem de erro incorreta");
    }

    // ========== Testes de extendDueDate ==========

    @Test
    @DisplayName("Deve estender prazo de devolução com sucesso")
    void testExtendDueDate() {
        controller.loan("user123", "978-0132350884", false);
        
        LocalDate newDate = LocalDate.now().plusDays(20);
        Optional<Loan> extended = controller.extendDueDate("user123", "978-0132350884", newDate);
        
        assertTrue(extended.isPresent(), "Deveria retornar o empréstimo");
        assertEquals(newDate, extended.get().getDueDate(), "Data de devolução deveria ter sido atualizada");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar estender prazo de empréstimo inexistente")
    void testExtendDueDateLoanNotFound() {
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.extendDueDate("user123", "978-0132350884", LocalDate.now().plusDays(20))
        );
        
        assertTrue(exception.getMessage().contains("Não há empréstimo aberto"), "Mensagem de erro incorreta");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar estender prazo com data inválida")
    void testExtendDueDateInvalid() {
        controller.loan("user123", "978-0132350884", false);
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.extendDueDate("user123", "978-0132350884", LocalDate.now().minusDays(1))
        );
        
        assertTrue(exception.getMessage().contains("Falha ao estender prazo"), "Mensagem de erro incorreta");
    }

    // ========== Testes de getLoansWithFilter ==========

    @Test
    @DisplayName("Deve retornar todos os empréstimos com filtro ALL")
    void testGetLoansWithFilterAll() {
        controller.loan("user123", "978-0132350884", false);
        controller.loan("user456", "978-0132350884", false);
        controller.returnLoanedBook("user123", "978-0132350884");
        
        List<Loan> loans = controller.getLoansWithFilter(LoanFilter.ALL);
        
        assertEquals(2, loans.size(), "Deveria ter 2 empréstimos");
    }

    @Test
    @DisplayName("Deve retornar apenas empréstimos abertos com filtro OPEN")
    void testGetLoansWithFilterOpen() {
        controller.loan("user123", "978-0132350884", false);
        controller.loan("user456", "978-0132350884", false);
        controller.returnLoanedBook("user123", "978-0132350884");
        
        List<Loan> openLoans = controller.getLoansWithFilter(LoanFilter.OPEN);
        
        assertEquals(1, openLoans.size(), "Deveria ter 1 empréstimo aberto");
        assertFalse(openLoans.get(0).isReturned(), "Empréstimo deveria estar aberto");
    }

    @Test
    @DisplayName("Deve retornar apenas empréstimos fechados com filtro CLOSED")
    void testGetLoansWithFilterClosed() {
        controller.loan("user123", "978-0132350884", false);
        controller.loan("user456", "978-0132350884", false);
        controller.returnLoanedBook("user123", "978-0132350884");
        
        List<Loan> closedLoans = controller.getLoansWithFilter(LoanFilter.CLOSED);
        
        assertEquals(1, closedLoans.size(), "Deveria ter 1 empréstimo fechado");
        assertTrue(closedLoans.get(0).isReturned(), "Empréstimo deveria estar devolvido");
    }

    // ========== Testes de listLoansSortedByLoanDateDesc ==========

    @Test
    @DisplayName("Deve listar empréstimos ordenados por data decrescente")
    void testListLoansSortedByLoanDateDesc() {
        BookController bc = new BookController();
        UserController uc = new UserController();
        
        bc.addBook("Book 1", "978-0132350884", "Author", "País", 5, true);
        bc.addBook("Book 2", "978-0306406157", "Author", "País", 5, true);
        bc.addBook("Book 3", "0306406152", "Author", "País", 5, true);
        
        uc.registerUser("User", "user123");
        
        LoanController lc = new LoanController(bc, uc);
        
        lc.loan("user123", "978-0306406157", false);
        lc.loan("user123", "978-0132350884", false);
        lc.loan("user123", "0306406152", false);
        
        List<Loan> sorted = lc.listLoansSortedByLoanDateDesc();
        
        assertEquals(3, sorted.size(), "Deveria ter 3 empréstimos");
        assertTrue(
            sorted.get(0).getLoanDate().isAfter(sorted.get(2).getLoanDate()) 
            || sorted.get(0).getLoanDate().equals(sorted.get(2).getLoanDate()),
            "Deveria estar ordenado por data decrescente"
        );
    }
}
