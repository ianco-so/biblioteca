package test.java.controller;

import main.java.controller.UserController;
import main.java.model.User;

import java.util.List;
import java.util.Optional;

public class UserControllerTest {

    public static void main(String[] args) {
        UserControllerTest test = new UserControllerTest();
        
        System.out.println("=== Testes do UserController ===");
        
        System.out.println("\n\t\tregisterUser()");
        test.testRegisterUser();
        test.testRegisterUserWithDuplicateId();
        test.testRegisterUserWithInvalidData();

        System.out.println("\n\t\tfindById()");
        test.testFindById();
        test.testFindByIdNotFound();
        
        System.out.println("\n\t\tgetAllUsers()");
        test.testGetAllUsers();
        
        System.out.println("\n\t\tgetUserLoanHistory()");
        test.testGetUserLoanHistory();
        test.testGetUserLoanHistoryUserNotFound();
        
        System.out.println("\n==========================");
    }

    private void printSuccess() {
        System.out.println("success!");
    }

    // ========== Testes de registerUser ==========

    public void testRegisterUser() {
        System.out.print("testRegisterUser: ");
        
        UserController controller = new UserController();
        User user = controller.registerUser("João Silva", "user123");
        
        assert user != null : "Usuário deveria ter sido criado";
        assert user.getName().equals("João Silva") : "Nome incorreto";
        assert user.getID().equals("user123") : "ID incorreto";
        assert controller.getAllUsers().size() == 1 : "Deveria ter 1 usuário";
        
        printSuccess();
    }

    public void testRegisterUserWithDuplicateId() {
        System.out.print("testRegisterUserWithDuplicateId: ");
        
        var controller = new UserController();
        controller.registerUser("João Silva", "user123");
        
        // Registrar novamente não deve adicionar outro usuário
        try {
            controller.registerUser("Maria Santos", "user123");
        } catch (IllegalStateException e) {
            assert e.getMessage().contains("já está cadastrado") : "Mensagem de erro incorreta";
        }
        assert controller.getAllUsers().size() == 1 : "Não deveria ter adicionado usuário duplicado";
        
        printSuccess();
    }

    public void testRegisterUserWithInvalidData() {
        System.out.print("testRegisterUserWithInvalidData: ");
        
        UserController controller = new UserController();
        
        try {
            controller.registerUser(null, "user123");
            assert false : "Deveria lançar IllegalArgumentException para nome null";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("não podem ser nulos") : "Mensagem de erro incorreta";
        }
        
        try {
            controller.registerUser("João Silva", "");
            assert false : "Deveria lançar IllegalArgumentException para ID vazio";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("não pode ser vazio") : "Mensagem de erro incorreta";
        }
        
        try {
            controller.registerUser("Jo", "user123");
            assert false : "Deveria lançar IllegalArgumentException para nome muito curto";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("muito curto") : "Mensagem de erro incorreta";
        }
        
        try {
            controller.registerUser("João Silva", "user@123");
            assert false : "Deveria lançar IllegalArgumentException para ID com caracteres especiais";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("alfanumérico") : "Mensagem de erro incorreta";
        }
        
        printSuccess();
    }

    // ========== Testes de findById ==========

    public void testFindById() {
        System.out.print("testFindById: ");
        
        UserController controller = new UserController();
        controller.registerUser("João Silva", "user123");
        
        Optional<User> found = controller.findById("user123");
        
        assert found.isPresent() : "Usuário deveria ter sido encontrado";
        assert found.get().getName().equals("João Silva") : "Nome incorreto";
        
        printSuccess();
    }

    public void testFindByIdNotFound() {
        System.out.print("testFindByIdNotFound: ");
        
        UserController controller = new UserController();
        controller.registerUser("João Silva", "user123");
        
        Optional<User> found = controller.findById("user999");
        
        assert found.isEmpty() : "Nenhum usuário deveria ter sido encontrado";
        
        printSuccess();
    }

    // ========== Testes de getAllUsers ==========

    public void testGetAllUsers() {
        System.out.print("testGetAllUsers: ");
        
        UserController controller = new UserController();
        controller.registerUser("João Silva", "user123");
        controller.registerUser("Maria Santos", "user456");
        controller.registerUser("Pedro Oliveira", "user789");
        
        List<User> users = controller.getAllUsers();
        
        assert users.size() == 3 : "Deveria ter 3 usuários";
        assert users.get(0).getName().equals("João Silva") : "Primeiro usuário incorreto";
        assert users.get(1).getName().equals("Maria Santos") : "Segundo usuário incorreto";
        assert users.get(2).getName().equals("Pedro Oliveira") : "Terceiro usuário incorreto";
        
        printSuccess();
    }

    // ========== Testes de getUserLoanHistory ==========

    public void testGetUserLoanHistory() {
        System.out.print("testGetUserLoanHistory: ");
        
        UserController controller = new UserController();
        controller.registerUser("João Silva", "user123");
        
        var loans = controller.getUserLoanHistory("user123");
        
        assert loans != null : "Histórico não deveria ser null";
        assert loans.size() == 0 : "Histórico deveria estar vazio";
        
        printSuccess();
    }

    public void testGetUserLoanHistoryUserNotFound() {
        System.out.print("testGetUserLoanHistoryUserNotFound: ");
        
        UserController controller = new UserController();
        
        try {
            controller.getUserLoanHistory("user999");
            assert false : "Deveria lançar IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("não encontrado") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }
}
