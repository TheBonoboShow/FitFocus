package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.SessionRepository;
import be.spacedandy.FitFocus.repositories.SportRepository;
import be.spacedandy.FitFocus.repositories.UserRepository;
import be.spacedandy.FitFocus.security.NoSessionsLeftException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {

    @Autowired private SessionRepository sessionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SportRepository sportRepository;

    public List<Session> getSessions(){
        return sessionRepository.findAllByOrderByDateAsc();
    }

    public List<Session> getFutureSessions() {
        return sessionRepository.findAllByOrderByDateAsc().stream().filter(s -> LocalDate.parse(s.getDate()).isAfter(LocalDate.now())).collect(Collectors.toList());
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

    public List<Session> getUserSessions(User user){
        List<Session> allSessions = getFutureSessions();
        List<Session> userSessions = new ArrayList<>();
        for (Session s : allSessions){
            if (s.getParticipants().contains(user)){
                userSessions.add(s);
            }
        }
        return userSessions;
    }

    public List<Session> findSessionsByUserId (int userid){
        List<Session> sessions = new ArrayList<>();
        for (Integer i : sessionRepository.findSessionsByUserid(userid)){
            sessions.add(sessionRepository.findById(i.intValue()));
        }
        return sessions;
    }

    public List<User> findUsersBySessionId (int sessionid){
        List<User> users = new ArrayList<>();
        for (Integer i : sessionRepository.findUsersBySessionid(sessionid)){
            users.add(userRepository.findById(i.intValue()));
        }
        return users;
    }

    public List<Session> getNonBookedSessions(User user){
        List<Session> sessions = getFutureSessions();
        List<Session> full = new ArrayList<>();
        List<Session> sessionsUser = getUserSessions(user);
        //removes sessions with no free spots
        for (Session s : sessions){
            if (s.getMaxParticipants() - findUsersBySessionId(s.getId()).size() <= 0){
                full.add(s);
            }
        }
        sessions.removeAll(full);
        //removes already booked sessions by user
        List<Session> sessionsNew = new ArrayList<>();
        if (sessions != null && sessionsUser != null){
            for (int i = 0; i<sessions.size(); i++){
                Session s = sessions.get(i);
                if (sessionsUser.contains(s)) {
                    sessionsUser.remove(s);
                    if (sessionsUser == null) break;
                } else {
                    sessionsNew.add(s);
                }
            }
        }

        //removes female only sessions if user is male
        if (!user.isFemale()){
            return sessionsNew.stream().filter(s -> !s.isOnlyFemales()).collect(Collectors.toList());
        }

        return sessionsNew;
    }

    public void save(Session session){
        sessionRepository.save(session);
    }

    public Session findById(int id){
        return sessionRepository.findById(id);
    }

    public Optional<Session> findByCoach(String name){
        User user = userRepository.findByUsername(name);
        return sessionRepository.findByCoach(user);
    }

    public void delete(int id) {
        //todo make a list of all the users that booked the deleted session, send a mail and add a session to their balance
        sessionRepository.deleteSession(id);
        sessionRepository.deleteById(id);
    }

    public void deleteSession(Integer id, User user) {
        sessionRepository.deleteUserSession(id, user.getId());
        user.setRemainingSessions(user.getRemainingSessions()+1);
        userRepository.save(user);
    }
}
