package test;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║         SISTEMA DE TESTES - BIBLIOTECA             ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println();
        
        boolean assertsEnabled = false;
        assert assertsEnabled = true;
        
        if (!assertsEnabled) {
            System.err.println("(X) ERRO: Assertions não estão habilitadas!");
            System.err.println("Execute com a flag -ea: java -ea -cp bin test.TestRunner");
            System.exit(1);
        }
        
        try {
            BookControllerTest.main(args);
            
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║             TODOS OS TESTES PASSARAM!              ║");
            System.out.println("╚════════════════════════════════════════════════════╝");
            
        } catch (AssertionError e) {
            System.err.println("\n(X) FALHA NO TESTE:");
            System.err.println("   " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("\n(X) ERRO DURANTE EXECUÇÃO DOS TESTES:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
