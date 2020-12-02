package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;


    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void save(User user){
        userRepository.save(user);
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

}
