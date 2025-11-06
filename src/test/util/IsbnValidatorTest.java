package test.util;

import main.util.IsbnValidator;

public class IsbnValidatorTest {

    public static void main(String[] args) {
        IsbnValidatorTest test = new IsbnValidatorTest();
        
        System.out.println("=== Testes do IsbnValidator ===");
        
        System.out.println("\n\t\tisValid()");
        test.testIsValidWithValidIsbn10();
        test.testIsValidWithInvalidIsbn10();
        test.testIsValidWithValidIsbn13();
        test.testIsValidWithInvalidIsbn13();

        test.testIsValidWithNull();
        test.testIsValidWithEmptyString();
        test.testIsValidWithInvalidLength();
        test.testIsValidWithLetters();
        
        System.out.println("\n\t\tgetCleanIsbn()");
        test.testGetCleanIsbn();
        test.testGetCleanIsbnWithNull();

        System.out.println("\n==========================");
    }

    private void printSuccess() {
        System.out.println("success!");
    }

    public void testIsValidWithValidIsbn10() {
        System.out.print("testIsValidWithValidIsbn10: ");
        
        boolean valid1 = IsbnValidator.isValid("0306406152");
        boolean valid2 = IsbnValidator.isValid("0201633612");
        boolean withX = IsbnValidator.isValid("043942089X");
        boolean withHyphens = IsbnValidator.isValid("0-306-40615-2");
        
        assert valid1 == true : "ISBN-10 0306406152 válido deveria retornar true";
        assert valid2 == true : "ISBN-10 0201633612 válido deveria retornar true";
        assert withX == true : "ISBN-10 com X no final deveria ser válido";
        assert withHyphens == true : "ISBN-10 com hífens deveria ser válido";
        
        printSuccess();
    }

    public void testIsValidWithInvalidIsbn10() {
        System.out.print("testIsValidWithInvalidIsbn10: ");
        
        boolean invalid = IsbnValidator.isValid("0306406153");
        boolean incorrectChecksum = IsbnValidator.isValid("0123456780");
        assert invalid == false : "ISBN-10 inválido deveria retornar false";
        assert incorrectChecksum == false : "ISBN-10 com checksum inválido deveria retornar false";

        printSuccess();
    }

    // ========== Testes para ISBN-13 ==========

    public void testIsValidWithValidIsbn13() {
        System.out.print("testIsValidWithValidIsbn13: ");
        
        boolean valid1 = IsbnValidator.isValid("9780306406157");
        boolean valid2 = IsbnValidator.isValid("9780201633610");
        boolean withSpaces = IsbnValidator.isValid("978 0 306 40615 7");
        
        assert valid1 == true : "ISBN-13 9780306406157 válido deveria retornar true";
        assert valid2 == true : "ISBN-13 9780201633610 válido deveria retornar true";
        assert withSpaces == true : "ISBN-13 com espaços deveria ser válido";
        
        printSuccess();
    }

    public void testIsValidWithInvalidIsbn13() {
        System.out.print("testIsValidWithInvalidIsbn13: ");
        
        boolean invalid1 = IsbnValidator.isValid("9780306406158");
        boolean invalid2 = IsbnValidator.isValid("9781234567890");
        
        assert invalid1 == false : "ISBN-13 inválido deveria retornar false";
        assert invalid2 == false : "ISBN-13 com checksum inválido deveria retornar false";
        
        printSuccess();
    }
    // ========== Testes de casos extremos ==========

    public void testIsValidWithNull() {
        System.out.print("testIsValidWithNull: ");
        
        boolean result = IsbnValidator.isValid(null);
        assert result == false : "ISBN null deveria retornar false";
        
        printSuccess();
    }

    public void testIsValidWithEmptyString() {
        System.out.print("testIsValidWithEmptyString: ");
        
        boolean result = IsbnValidator.isValid("");
        assert result == false : "String vazia deveria retornar false";
        
        printSuccess();
    }

    public void testIsValidWithInvalidLength() {
        System.out.print("testIsValidWithInvalidLength: ");
        
        boolean result1 = IsbnValidator.isValid("123456789");
        boolean result2 = IsbnValidator.isValid("12345678901");
        boolean result3 = IsbnValidator.isValid("123456789012");
        
        assert result1 == false : "ISBN com 9 dígitos deveria ser inválido";
        assert result2 == false : "ISBN com 11 dígitos deveria ser inválido";
        assert result3 == false : "ISBN com 12 dígitos deveria ser inválido";

        printSuccess();
    }

    public void testIsValidWithLetters() {
        System.out.print("testIsValidWithLetters: ");
        
        boolean result = IsbnValidator.isValid("01323A0884");
        assert result == false : "ISBN com letras (exceto X) deveria ser inválido";
        
        printSuccess();
    }

    // ========== Testes para getCleanIsbn ==========

    public void testGetCleanIsbn() {
        System.out.print("testGetCleanIsb: ");
        
        String result1 = IsbnValidator.getCleanIsbn("978-0-306-40615-7");
        String result2 = IsbnValidator.getCleanIsbn("978 0 306 40615 7");
        assert result1.equals("9780306406157") : "Hífens deveriam ser removidos";
        assert result2.equals("9780306406157") : "Espaços deveriam ser removidos";
        
        printSuccess();
    }

    public void testGetCleanIsbnWithNull() {
        System.out.print("testGetCleanIsbnWithNull: ");
        
        try {
            IsbnValidator.getCleanIsbn(null);
            assert false : "Deveria lançar IllegalArgumentException para null";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("ISBN não pode ser nulo.") : "Mensagem de erro incorreta";
            printSuccess();
        }
    }
}
