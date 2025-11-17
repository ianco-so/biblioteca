package main.java.util;

public final class IsbnValidator {

    public static boolean isValid(String isbn) {
        if (isbn == null) {
            return false;
        }

        String cleanedIsbn = isbn.replaceAll("[\\s-]+", "");

        if (cleanedIsbn.length() == 10) {
            return isValidIsbn10(cleanedIsbn);
        } else if (cleanedIsbn.length() == 13) {
            return isValidIsbn13(cleanedIsbn);
        } else {
            return false;
        }
    }

    public static String getCleanIsbn(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN não pode ser nulo.");
        }
        return isbn.replaceAll("[-\\s]", "");
    }

    private static boolean isValidIsbn10(String isbn) {
        if (!isbn.matches("^[0-9]{9}[0-9X]$")) {
            return false;
        }
        
        var sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
        }

        var lastChar = isbn.charAt(9);
        sum += lastChar == 'X' ? 10 : Character.getNumericValue(lastChar);

        return sum % 11 == 0;
    }

    private static boolean isValidIsbn13(String isbn) {
        if (!isbn.matches("^[0-9]{13}$")) return false;

        var sum = 0;
        for (var i = 0; i < 13; i++) {
            var digit = Character.getNumericValue(isbn.charAt(i));
            var weight = (i % 2 == 0) ? 1 : 3;
            sum += digit * weight;
        }

        return sum % 10 == 0;
    }
}