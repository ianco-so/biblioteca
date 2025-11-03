package main.view;

import main.controller.UserController;
import main.model.User;

import java.util.List;
import java.util.Scanner;

public class UserView {

    private static Scanner scanner = new Scanner(System.in);

    public static void cadastrarUsuario(UserController userService){
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

    public static void listarUsuarios (UserController userService){
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
    
}
