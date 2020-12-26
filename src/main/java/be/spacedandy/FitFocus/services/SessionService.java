package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.SessionRepository;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired private SessionRepository sessionRepository;
    @Autowired private UserRepository userRepository;


    public List<Session> getSessions(){
        return sessionRepository.findAll();
    }

    public void save(Session session){
        sessionRepository.save(session);
    }

    public Optional<Session> findById(int id){
        return sessionRepository.findById(id);
    }

    public Optional<Session> findByCoach(String name){
        User user = userRepository.findByUsername(name);
        return sessionRepository.findByCoach(user);
    }

    public void delete(int id) {
        sessionRepository.deleteById(id);
    }
}
