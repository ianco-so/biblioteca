package view;

import controller.UserController;

public class UserViewTest {

    public static void main(String[] args) {
        UserViewTest test = new UserViewTest();
        
        System.out.println("=== Testes do UserView ===");
        
        System.out.println("\n\t\tTeste básico");
        test.testUserControllerIntegration();
        
        System.out.println("\n==========================");
    }

    private void printSuccess() {
        System.out.println("success!");
    }

    public void testUserControllerIntegration() {
        System.out.print("testUserControllerIntegration: ");
        
        UserController controller = new UserController();
        
        controller.registerUser("João Silva", "user123");
        controller.registerUser("Maria Santos", "user456");
        
        var users = controller.getAllUsers();
        assert users.size() == 2 : "Controller deveria ter 2 usuários";
        assert users.get(0).getName().equals("João Silva") : "Primeiro nome incorreto";
        assert users.get(1).getName().equals("Maria Santos") : "Segundo nome incorreto";
        
        printSuccess();
    }
}
