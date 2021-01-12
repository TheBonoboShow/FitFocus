package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.SessionRepository;
import be.spacedandy.FitFocus.repositories.SportRepository;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired private SessionRepository sessionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SportRepository sportRepository;


    public List<Session> getSessions(){
        return sessionRepository.findAll();
    }

    public List<Session> getSessionsYoga(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Yoga"));
    }

    public List<Session> getSessionsBoxing(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Boxing"));
    }
    public List<Session> getSessionsCardio(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Cardio"));
    }
    public List<Session> getSessionsAerobic(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Aerobic"));
    }
    public List<Session> getSessionsCycling(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Cycling"));
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
