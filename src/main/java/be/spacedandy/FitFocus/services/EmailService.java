package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {
    @Autowired UserRepository userRepository;

    public List<User> getAllUsersWithPromo(){
        return userRepository.findAll().stream().filter(u -> u.isPromotionsActive() == true).collect(Collectors.toList());
    }
}
