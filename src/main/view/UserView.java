package main.view;

import main.controller.UserController;
import main.model.User;

import java.util.List;

public class UserView implements MenuView {

    public static void menu(UserController userService) {
        var goBack = false;

        while (!goBack) {
            showMenuOptions();
            var option = MenuView.readOption();

            switch (option) {
                case 1:
                    createUser(userService);
                    break;
                case 2:
                    getUsers(userService);
                    break;
                case 0:
                    goBack = true;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void createUser(UserController userService){
        System.out.println("\n--- CADASTRO DE USUÁRIO ---");

        System.out.print("Digite o nome do usuário: ");
        String name = scanner.nextLine();

        System.out.print("Digite o ID do usuário: ");
        String id = scanner.nextLine();

        if(userService.findUser(id) != null){
            System.out.println("\n -- Esse usuário já está cadastrado! -- ");
        } else {
            try{
                userService.registerUser(name, id);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            }
        }
        
    }

    private static void getUsers(UserController userService){
        List<User> users = userService.getAllUsers();

        if(users.isEmpty()){
            System.out.println("Nenhum usuário cadastrado no sistema.");
        } else {
            System.out.println("Total de usuários: " + users.size());
            System.out.println();

            for(int i = 0; i < users.size(); i++){
                User user = users.get(i);
                System.out.printf("%d. Nome: %-30s ID: %-20s %n", 
                    (i + 1), 
                    user.getName(), 
                    user.getID()
                );
            }
        }
    }

    private static void showMenuOptions() {
        System.out.println("\n=== MENU DE USUÁRIOS ===");
        System.out.println("1. Cadastrar usuário");
        System.out.println("2. Listar usuários");
        System.out.println("0. Voltar");
    }
}
