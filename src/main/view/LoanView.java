package main.view;

import main.controller.LoanController;
import main.model.Loan;
import main.model.enums.LoanFilter;
import main.util.IsbnValidator;

import java.time.LocalDate;

public class LoanView implements MenuView {

    public static void menu(LoanController loanController) {
        while (true) {
            showMenuOptions();

            var option = MenuView.readOption();
            switch (option  ) {
                case 1:
                    emprestar(loanController, false);
                    break;
                case 2:
                    emprestar(loanController, true);
                    break;
                case 3:
                    devolver(loanController);
                    break;
                case 4:
                    estenderPrazo(loanController);
                    break;
                case 5:
                    listarEmprestimos(loanController, LoanFilter.OPEN);
                    break;
                case 6:
                    listarEmprestimos(loanController, LoanFilter.CLOSED);
                    break;
                case 7:
                    listarEmprestimos(loanController, LoanFilter.ALL);
                    break;
                case 0:
                    return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private static void emprestar(LoanController lc, boolean isDigital) {
        System.out.print("ID do usuário: ");
        String userId = scanner.nextLine().trim();
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine().trim();
        isbn = IsbnValidator.getCleanIsbn(isbn);

        try {
            var loan = lc.loan(userId, isbn, isDigital);
            System.out.println("Empréstimo efetivado!\n" + loan);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao realizar empréstimo: " + e.getMessage());
        }
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

    private static void listarEmprestimos (LoanController lc, LoanFilter filter) {
        var laons = lc.getLoansWithFilter(filter);
        if (laons.isEmpty()) {
            System.out.println("Nenhum empréstimo encontrado para o filtro selecionado.");
            return;
        }
        for (Loan l : laons) System.out.println(l);
    }

    private static void showMenuOptions() {
        System.out.println("\n=== EMPRÉSTIMOS ===");
        System.out.println("1. Emprestar LIVRO FÍSICO");
        System.out.println("2. Emprestar LIVRO DIGITAL");
        System.out.println("3. Devolver livro");
        System.out.println("4. Estender prazo");
        System.out.println("5. Listar empréstimos em ABERTO");
        System.out.println("6. Listar empréstimos DEVOLVIDOS");
        System.out.println("7. Listar TODOS (mais recentes primeiro)");
        System.out.println("0. Voltar");
    }
}
