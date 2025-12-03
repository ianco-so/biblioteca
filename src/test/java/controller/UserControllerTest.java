package controller;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do UserController")
class UserControllerTest {

    private UserController controller;

    @BeforeEach
    void setUp() {
        controller = new UserController();
    }

    // ========== Testes de registerUser ==========

    @Test
    @DisplayName("Deve registrar um usuário com sucesso")
    void testRegisterUser() {
        User user = controller.registerUser("João Silva", "user123");
        
        assertNotNull(user, "Usuário deveria ter sido criado");
        assertEquals("João Silva", user.getName(), "Nome incorreto");
        assertEquals("user123", user.getID(), "ID incorreto");
        assertEquals(1, controller.getAllUsers().size(), "Deveria ter 1 usuário");
    }

    @Test
    @DisplayName("Deve lançar exceção ao registrar usuário com ID duplicado")
    void testRegisterUserWithDuplicateId() {
        controller.registerUser("João Silva", "user123");
        
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> controller.registerUser("Maria Santos", "user123")
        );
        
        assertTrue(exception.getMessage().contains("já está cadastrado"), "Mensagem de erro incorreta");
        assertEquals(1, controller.getAllUsers().size(), "Não deveria ter adicionado usuário duplicado");
    }

    @Test
    @DisplayName("Deve lançar exceção ao registrar usuário com dados inválidos")
    void testRegisterUserWithInvalidData() {
        // Nome null
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> controller.registerUser(null, "user123")
        );
        assertTrue(exception1.getMessage().contains("não podem ser nulos"), "Mensagem de erro incorreta");
        
        // ID vazio
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> controller.registerUser("João Silva", "")
        );
        assertTrue(exception2.getMessage().contains("não pode ser vazio"), "Mensagem de erro incorreta");
        
        // Nome muito curto
        IllegalArgumentException exception3 = assertThrows(
            IllegalArgumentException.class,
            () -> controller.registerUser("Jo", "user123")
        );
        assertTrue(exception3.getMessage().contains("muito curto"), "Mensagem de erro incorreta");
        
        // ID com caracteres especiais
        IllegalArgumentException exception4 = assertThrows(
            IllegalArgumentException.class,
            () -> controller.registerUser("João Silva", "user@123")
        );
        assertTrue(exception4.getMessage().contains("alfanumérico"), "Mensagem de erro incorreta");
    }

    // ========== Testes de findById ==========

    @Test
    @DisplayName("Deve encontrar usuário por ID")
    void testFindById() {
        controller.registerUser("João Silva", "user123");
        
        Optional<User> found = controller.findById("user123");
        
        assertTrue(found.isPresent(), "Usuário deveria ter sido encontrado");
        assertEquals("João Silva", found.get().getName(), "Nome incorreto");
    }

    @Test
    @DisplayName("Deve retornar vazio quando usuário não for encontrado")
    void testFindByIdNotFound() {
        controller.registerUser("João Silva", "user123");
        
        Optional<User> found = controller.findById("user999");
        
        assertTrue(found.isEmpty(), "Nenhum usuário deveria ter sido encontrado");
    }

    // ========== Testes de getAllUsers ==========

    @Test
    @DisplayName("Deve retornar todos os usuários cadastrados")
    void testGetAllUsers() {
        controller.registerUser("João Silva", "user123");
        controller.registerUser("Maria Santos", "user456");
        controller.registerUser("Pedro Oliveira", "user789");
        
        List<User> users = controller.getAllUsers();
        
        assertEquals(3, users.size(), "Deveria ter 3 usuários");
        assertEquals("João Silva", users.get(0).getName(), "Primeiro usuário incorreto");
        assertEquals("Maria Santos", users.get(1).getName(), "Segundo usuário incorreto");
        assertEquals("Pedro Oliveira", users.get(2).getName(), "Terceiro usuário incorreto");
    }

    // ========== Testes de getUserLoanHistory ==========

    @Test
    @DisplayName("Deve retornar histórico vazio para usuário novo")
    void testGetUserLoanHistory() {
        controller.registerUser("João Silva", "user123");
        
        var loans = controller.getUserLoanHistory("user123");
        
        assertNotNull(loans, "Histórico não deveria ser null");
        assertEquals(0, loans.size(), "Histórico deveria estar vazio");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar histórico de usuário inexistente")
    void testGetUserLoanHistoryUserNotFound() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> controller.getUserLoanHistory("user999")
        );
        
        assertTrue(exception.getMessage().contains("não encontrado"), "Mensagem de erro incorreta");
    }
}
