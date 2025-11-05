package main.view;

import java.util.Scanner;

public interface MenuView {
    Scanner scanner = new Scanner(System.in);

    public static int readOption() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
