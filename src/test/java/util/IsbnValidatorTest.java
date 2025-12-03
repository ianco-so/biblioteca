package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do IsbnValidator")
class IsbnValidatorTest {

    // ========== Testes para ISBN-10 ==========

    @Test
    @DisplayName("Deve validar ISBN-10 válido")
    void testIsValidWithValidIsbn10() {
        assertTrue(IsbnValidator.isValid("0306406152"));
        assertTrue(IsbnValidator.isValid("0201633612"));
        assertTrue(IsbnValidator.isValid("043942089X"), "ISBN-10 com X no final deveria ser válido");
        assertTrue(IsbnValidator.isValid("0-306-40615-2"), "ISBN-10 com hífens deveria ser válido");
    }

    @Test
    @DisplayName("Deve rejeitar ISBN-10 inválido")
    void testIsValidWithInvalidIsbn10() {
        assertFalse(IsbnValidator.isValid("0306406153"));
        assertFalse(IsbnValidator.isValid("0123456780"), "ISBN-10 com checksum inválido deveria retornar false");
    }

    // ========== Testes para ISBN-13 ==========

    @Test
    @DisplayName("Deve validar ISBN-13 válido")
    void testIsValidWithValidIsbn13() {
        assertTrue(IsbnValidator.isValid("9780306406157"));
        assertTrue(IsbnValidator.isValid("9780201633610"));
        assertTrue(IsbnValidator.isValid("978 0 306 40615 7"), "ISBN-13 com espaços deveria ser válido");
    }

    @Test
    @DisplayName("Deve rejeitar ISBN-13 inválido")
    void testIsValidWithInvalidIsbn13() {
        assertFalse(IsbnValidator.isValid("9780306406158"));
        assertFalse(IsbnValidator.isValid("9781234567890"), "ISBN-13 com checksum inválido deveria retornar false");
    }

    // ========== Testes de casos extremos ==========

    @Test
    @DisplayName("Deve rejeitar ISBN null")
    void testIsValidWithNull() {
        assertFalse(IsbnValidator.isValid(null), "ISBN null deveria retornar false");
    }

    @Test
    @DisplayName("Deve rejeitar string vazia")
    void testIsValidWithEmptyString() {
        assertFalse(IsbnValidator.isValid(""), "String vazia deveria retornar false");
    }

    @Test
    @DisplayName("Deve rejeitar ISBN com tamanho inválido")
    void testIsValidWithInvalidLength() {
        assertFalse(IsbnValidator.isValid("123456789"), "ISBN com 9 dígitos deveria ser inválido");
        assertFalse(IsbnValidator.isValid("12345678901"), "ISBN com 11 dígitos deveria ser inválido");
        assertFalse(IsbnValidator.isValid("123456789012"), "ISBN com 12 dígitos deveria ser inválido");
    }

    @Test
    @DisplayName("Deve rejeitar ISBN com letras (exceto X)")
    void testIsValidWithLetters() {
        assertFalse(IsbnValidator.isValid("01323A0884"), "ISBN com letras (exceto X) deveria ser inválido");
    }

    // ========== Testes para getCleanIsbn ==========

    @Test
    @DisplayName("Deve remover hífens e espaços do ISBN")
    void testGetCleanIsbn() {
        assertEquals("9780306406157", IsbnValidator.getCleanIsbn("978-0-306-40615-7"), "Hífens deveriam ser removidos");
        assertEquals("9780306406157", IsbnValidator.getCleanIsbn("978 0 306 40615 7"), "Espaços deveriam ser removidos");
    }

    @Test
    @DisplayName("Deve lançar exceção para ISBN null")
    void testGetCleanIsbnWithNull() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> IsbnValidator.getCleanIsbn(null)
        );
        assertEquals("ISBN não pode ser nulo.", exception.getMessage());
    }
}
