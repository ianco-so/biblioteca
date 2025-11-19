package controller;

import model.Loan;
import model.User;

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
     * @throws IllegalStateException caso o usuário já esteja cadastrado
     */
    public User registerUser(String name, String id){
        if(this.findById(id).isPresent()){
            throw new IllegalStateException("Esse usuário já está cadastrado");
        }
        
        User user = new User(name, id);
        this.users.add(user);
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

    public List<Loan> getUserLoanHistory(String userId) {
        var userOpt = findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return userOpt.get().getLoanHistory();
    }
}
