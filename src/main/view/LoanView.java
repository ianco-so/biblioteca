package main.view;

import main.controller.LoanController;
import main.model.Loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LoanView {
    private static final Scanner scanner = new Scanner(System.in);

    public static void menu(LoanController loanController) {
        while (true) {
            System.out.println("\n=== EMPRÉSTIMOS ===");
            System.out.println("1. Emprestar LIVRO FÍSICO");
            System.out.println("2. Emprestar LIVRO DIGITAL");
            System.out.println("3. Devolver livro");
            System.out.println("4. Estender prazo");
            System.out.println("5. Listar empréstimos em ABERTO");
            System.out.println("6. Listar empréstimos DEVOLVIDOS");
            System.out.println("7. Listar TODOS (mais recentes primeiro)");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1":
                    emprestarFisico(loanController);
                    break;
                case "2":
                    emprestarDigital(loanController);
                    break;
                case "3":
                    devolver(loanController);
                    break;
                case "4":
                    estenderPrazo(loanController);
                    break;
                case "5":
                    listarAbertos(loanController);
                    break;
                case "6":
                    listarDevolvidos(loanController);
                    break;
                case "7":
                    listarTodos(loanController);
                    break;
                case "0":
                    return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private static void emprestarFisico(LoanController lc) {
        System.out.print("ID do usuário: ");
        String userId = scanner.nextLine().trim();
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine().trim();

        boolean ok = lc.loanPhysical(userId, isbn);
        if (!ok) System.out.println("Não foi possível realizar o empréstimo físico.");
    }

    private static void emprestarDigital(LoanController lc) {
        System.out.print("ID do usuário: ");
        String userId = scanner.nextLine().trim();
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine().trim();

        boolean ok = lc.loanDigital(userId, isbn);
        if (!ok) System.out.println("Não foi possível realizar o empréstimo digital.");
    }

    private static void devolver(LoanController lc) {
        System.out.print("ID do usuário: ");
        String userId = scanner.nextLine().trim();
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine().trim();

        boolean ok = lc.returnBook(userId, isbn);
        if (!ok) System.out.println("Não foi possível devolver (nenhum empréstimo em aberto encontrado?).");
    }

    private static void estenderPrazo(LoanController lc) {
        System.out.print("ID do usuário: ");
        String userId = scanner.nextLine().trim();
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Nova data (AAAA-MM-DD): ");
        String dateStr = scanner.nextLine().trim();

        try {
            LocalDate newDate = LocalDate.parse(dateStr);
            boolean ok = lc.extendDueDate(userId, isbn, newDate);
            if (!ok) System.out.println("Falha ao estender prazo.");
        } catch (Exception e) {
            System.out.println("Data inválida. Use o formato AAAA-MM-DD.");
        }
    }

    private static void listarAbertos(LoanController lc) {
        List<Loan> list = lc.listOpenLoans();
        if (list.isEmpty()) {
            System.out.println("Nenhum empréstimo em aberto.");
            return;
        }
        for (Loan l : list) System.out.println(l);
    }

    private static void listarDevolvidos(LoanController lc) {
        List<Loan> list = lc.listClosedLoans();
        if (list.isEmpty()) {
            System.out.println("Nenhum empréstimo devolvido.");
            return;
        }
        for (Loan l : list) System.out.println(l);
    }

    private static void listarTodos(LoanController lc) {
        List<Loan> list = lc.listLoansSortedByLoanDateDesc();
        if (list.isEmpty()) {
            System.out.println("Nenhum empréstimo registrado.");
            return;
        }
        for (Loan l : list) System.out.println(l);
    }
}
