package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.EmailAlreadyExistException;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.models.UserAlreadyExistException;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(User user) throws UserAlreadyExistException, EmailAlreadyExistException {

        if (checkIfUserExistName(user.getUsername())) {
            throw new UserAlreadyExistException("This user already has a registered account");
        }
        if (checkIfUserExistMail(user.getEmail())) {
            throw new EmailAlreadyExistException("User already exists for this email");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public boolean checkIfUserExistMail(String email) {
        return userRepository.findByEmail(email) != null ? true : false;
    }

    public boolean checkIfUserExistName(String name) {
        return userRepository.findByUsername(name) != null ? true : false;
    }
}
