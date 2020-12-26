package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.models.UserAlreadyExistException;
import be.spacedandy.FitFocus.repositories.UserRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveAdmin(User user){
        userRepository.save(user);
    }

    public User findById(int id){
        return userRepository.findById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

}
