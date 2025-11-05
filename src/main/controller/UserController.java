package main.controller;

import main.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private final List<User> users = new ArrayList<>();

    //Substituir essa verificao no registerUser por uma funcao tambem
    public User registerUser(String name, String id){
        if(name == null || id == null){
            throw new IllegalArgumentException("Nome e ID do usuário são campos obrigatórios.");
        }

        name = name.trim();
        id = id.trim();

        User user = new User(name, id);
        if(findUser(id) == null){
            users.add(user);
        } else {
            return null;
        }
       
        return user;
    }

    //Usar uma funcao assim para validar os campos previamente
    public boolean userDataValidator(String name, String id){
        name = name.trim();
        id = id.trim();
        if(name.isEmpty() || id.isEmpty() || name.length() < 3 || id.length() < 3){
            return false;
        } else {
            return true;
        }
    }

    public User findUser(String id){
        for(User user: users){
            if(user.getID().equals(id)){
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers(){
        return new ArrayList<> (this.users);
    }


}
