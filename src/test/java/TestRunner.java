

import util.IsbnValidatorTest;
import controller.BookControllerTest;
import controller.UserControllerTest;
import controller.LoanControllerTest;
import view.BookViewTest;
import view.UserViewTest;
import view.LoanViewTest;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    SISTEMA DE TESTES - BIBLIOTECA");
        System.out.println("========================================\n");
        
        boolean assertsEnabled = false;
        assert assertsEnabled = true;
        
        if (!assertsEnabled) {
            System.err.println("ERRO: Assertions nao estao habilitadas!");
            System.err.println("Execute com: java -ea -cp bin test.TestRunner");
            System.exit(1);
        }
        
        try {
            IsbnValidatorTest.main(args);
            System.out.println();
            
            BookControllerTest.main(args);
            System.out.println();
            
            UserControllerTest.main(args);
            System.out.println();
            
            LoanControllerTest.main(args);
            System.out.println();
            
            BookViewTest.main(args);
            System.out.println();
            
            UserViewTest.main(args);
            System.out.println();
            
            LoanViewTest.main(args);
            
            System.out.println("\n========================================");
            System.out.println("      TODOS OS TESTES PASSARAM!");
            System.out.println("========================================");
            
        } catch (AssertionError e) {
            System.err.println("\nFALHA NO TESTE:");
            System.err.println("  " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("\nERRO DURANTE EXECUCAO DOS TESTES:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
