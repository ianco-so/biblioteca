package main.controller;

import main.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserController {
    private final List<User> users = new ArrayList<>();
    
    /**
     * Registra um novo usuário. Caso o ID já exista, não registra novamente.
     * @param name
     * @param id
     * @return Usuário registrado ou existente
     * @throws IllegalArgumentException
     */
    public User registerUser(String name, String id){
        User user = new User(name, id);
        if(!this.findById(id).isPresent()){
            this.users.add(user);
        }
       
        return user;
    }

    /**
     * Registra um novo usuário. Caso o ID já exista, não registra novamente.
     * @param name
     * @param id
     * @return Usuário registrado ou existente
     * @throws IllegalArgumentException
     */
    public Optional<User> findById(String id){
        return this.users.stream()
                        .filter(user -> user.getID().equals(id))
                        .findFirst();
    }

    public List<User> getAllUsers(){
        return new ArrayList<> (this.users);
    }
}
