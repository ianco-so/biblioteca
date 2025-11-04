package main.controller;

import main.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private final List<User> users = new ArrayList<>();

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
