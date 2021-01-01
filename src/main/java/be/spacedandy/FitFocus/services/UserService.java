package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.security.WrongPasswordException;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void update(User user) throws WrongPasswordException{
        if (!checkIfPasswordMatches(user)) {
            throw new WrongPasswordException("The password is not correct");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private boolean checkIfPasswordMatches(User user) {
        String pass = findById(user.getId()).getPassword();
        return bCryptPasswordEncoder.matches(user.getPassword(), pass);
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
