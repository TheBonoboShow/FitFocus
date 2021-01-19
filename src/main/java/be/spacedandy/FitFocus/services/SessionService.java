package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.SessionRepository;
import be.spacedandy.FitFocus.repositories.SportRepository;
import be.spacedandy.FitFocus.repositories.UserRepository;
import be.spacedandy.FitFocus.security.NoSessionsLeftException;
import be.spacedandy.FitFocus.security.SessionOverlapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
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
    @Autowired private JavaMailSender javaMailSender;

    public List<Session> getSessions(){
        return sessionRepository.findAllByOrderByDateAsc();
    }

    public List<Session> getSessionsCalendar(){
        return sessionRepository.findAllByOrderByDateAsc().stream().filter(s -> LocalDate.parse(s.getDate()).plusDays(7).isAfter(LocalDate.now())).collect(Collectors.toList());
    }

    public List<Session> getFutureSessions() {
        return sessionRepository.findAllByOrderByDateAsc().stream().filter(s -> LocalDate.parse(s.getDate()).plusDays(1).isAfter(LocalDate.now())).collect(Collectors.toList());
    }

    public List<Session> getPastSessions() {
        return sessionRepository.findAllByOrderByDateAsc().stream().filter(s -> LocalDate.parse(s.getDate()).plusDays(1).isBefore(LocalDate.now())).collect(Collectors.toList());
    }

    public List<Session> getSessionsYoga(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Yoga")).stream().filter(s -> LocalDate.parse(s.getDate()).plusDays(7).isAfter(LocalDate.now())).collect(Collectors.toList());
    }

    public List<Session> getSessionsBoxing(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Boxing")).stream().filter(s -> LocalDate.parse(s.getDate()).plusDays(7).isAfter(LocalDate.now())).collect(Collectors.toList());
    }
    public List<Session> getSessionsCardio(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Cardio")).stream().filter(s -> LocalDate.parse(s.getDate()).plusDays(7).isAfter(LocalDate.now())).collect(Collectors.toList());
    }
    public List<Session> getSessionsAerobic(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Aerobic")).stream().filter(s -> LocalDate.parse(s.getDate()).plusDays(7).isAfter(LocalDate.now())).collect(Collectors.toList());
    }
    public List<Session> getSessionsCycling(){
        return sessionRepository.findAllBySport(sportRepository.findByName("Cycling")).stream().filter(s -> LocalDate.parse(s.getDate()).plusDays(7).isAfter(LocalDate.now())).collect(Collectors.toList());
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

    public void save(Session session) throws SessionOverlapException {
        if (!checkSessionOverlap(session)){
            throw new SessionOverlapException("Multiple sessions can not be held at the same time");
        }
        sessionRepository.save(session);
    }

    private boolean checkSessionOverlap(Session session) {
        List<Session> sessions = getSessionsByDate(session.getDate());
        LocalTime startTime1 = LocalTime.parse(session.getStartingHour());
        LocalTime endTime1 = LocalTime.parse(session.getEndHour());
        for (Session s : sessions){
            LocalTime startTime2 = LocalTime.parse(s.getStartingHour());
            LocalTime endTime2 = LocalTime.parse(s.getEndHour());
            if (startTime1.isBefore(endTime2) && startTime2.isBefore(endTime1)){
                return false;
            }
        }
        return true;
    }

    private List<Session> getSessionsByDate(String date){
        return sessionRepository.findAllByDate(date);
    }

    public Session findById(int id){
        return sessionRepository.findById(id);
    }

    public Optional<Session> findByCoach(String name){
        User user = userRepository.findByUsername(name);
        return sessionRepository.findByCoach(user);
    }

    //this deletes the whole session and every participant
    public void delete(int id) throws UnsupportedEncodingException, MessagingException {
        List<User> users = findUsersBySessionId(id);
        Session s = findById(id);
        for (User u : users){
            u.setRemainingSessions(u.getRemainingSessions()+1);
            userRepository.save(u);
            sendMailDeletedession(u, s);
        }
        sessionRepository.deleteSession(id);
        sessionRepository.deleteById(id);
    }

    private void sendMailDeletedession(User user, Session session) throws UnsupportedEncodingException, MessagingException {
        User fullUser = userRepository.findByEmail(user.getEmail());
        String subject = "FitFocus session cancelled";
        String senderName = "FitFocus Team";
        String content = "<p>Dear " + fullUser.getUsername() + ", </p>";
        content += "<p> Due to unforeseen circumstances a session you booked has been cancelled at our gym.</p>";
        content += "<p>It consists of the session with the following information: <p>";
        content += "<p>Sport: " + session.getSport() + "<p>";
        content += "<p>Date: " + session.getDate() + "<p>";
        content += "<p>Starting Time: " + session.getStartingHour() +"<p>";
        content += "<br><p>We apologize for the inconvenience,<p>";
        content += "<br><p>The FitFocus Team<p>";
        content += "<img src='cid:logo' style='height: 65px; width: auto'/>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("ibo@staes.me", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        FileSystemResource image = new FileSystemResource(new File("src/main/resources/static/img/logo.jpg"));
        helper.setText(content, true);
        helper.addInline("logo", image);

        javaMailSender.send(message);
    }

    //this is for deleting a personal booked session per user
    public void deleteSession(Integer id, User user) {
        sessionRepository.deleteUserSession(id, user.getId());
        user.setRemainingSessions(user.getRemainingSessions()+1);
        userRepository.save(user);
    }

    public Page<Session> findPaginated(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize);
        return this.sessionRepository.findByDateIsAfterOrderByDateAsc(LocalDate.now().minusDays(1).toString(), pageable);
    }

    public Page<Session> findPaginatedPast(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize);
        return this.sessionRepository.findByDateIsBeforeOrderByDateDesc(LocalDate.now().toString(), pageable);
    }
}
