package view;

import controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do UserView")
class UserViewTest {

    @Test
    @DisplayName("Deve integrar com UserController corretamente")
    void testUserControllerIntegration() {
        UserController controller = new UserController();
        
        controller.registerUser("João Silva", "user123");
        controller.registerUser("Maria Santos", "user456");
        
        var users = controller.getAllUsers();
        assertEquals(2, users.size(), "Controller deveria ter 2 usuários");
        assertEquals("João Silva", users.get(0).getName(), "Primeiro nome incorreto");
        assertEquals("Maria Santos", users.get(1).getName(), "Segundo nome incorreto");
    }
}
