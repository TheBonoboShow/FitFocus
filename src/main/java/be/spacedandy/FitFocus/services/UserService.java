package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.security.NoSessionsLeftException;
import be.spacedandy.FitFocus.security.NoValidSubscriptionException;
import be.spacedandy.FitFocus.security.UserNotFoundException;
import be.spacedandy.FitFocus.security.WrongPasswordException;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private JavaMailSender javaMailSender;

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

    public void addSessionToUser(Session session, User user) throws NoSessionsLeftException, NoValidSubscriptionException {
        if (!checkIfUserHasSession(user)){
            throw new NoSessionsLeftException("User has no valid sessions");
        }
        if (!checkIfValidSubscription(user, session)){
            throw new NoValidSubscriptionException("User has no running subsciption");
        }
        List<Session> ss = user.getReservedSessions();
        ss.add(session);
        user.setReservedSessions(ss);
        user.setRemainingSessions(user.getRemainingSessions()-1);
        saveAdmin(user);
    }


    public boolean checkIfUserHasSession(User user) {
        return user.getRemainingSessions() > 0;
    }

    private boolean checkIfValidSubscription(User user, Session session) {
        LocalDate sessionDate = LocalDate.parse(session.getDate());
        if (LocalDate.parse(user.getStartDate()).isAfter(sessionDate)){
            return false;
        }
        if (LocalDate.parse(user.getEndDate()).isBefore(sessionDate)){
            return false;
        }
        return true;
    }

    public void update(User user) throws WrongPasswordException{
        if (!checkIfPasswordMatches(user)) {
            throw new WrongPasswordException("The password is not correct");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean checkIfPasswordMatches(User user) {
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
        userRepository.deleteSessionsUser(id);
        userRepository.deleteById(id); //delete does not work if user is also coaching a session
    }

    public void resetPassword (String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null){
            user.setPasswordResetToken(token);
            userRepository.save(user);
        } else throw new UserNotFoundException("Could not find an account with this email: " + email);
    }

    public void updatePassword (User user){
        User oldUser = userRepository.findByPasswordResetToken(user.getPasswordResetToken());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        oldUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        oldUser.setPasswordResetToken(null);
        userRepository.save(oldUser);
    }

    public void sendVerificationEmail(User user, String url) throws UnsupportedEncodingException, MessagingException {
        User fullUser = userRepository.findByEmail(user.getEmail());
        String subject = "FitFocus password reset";
        String senderName = "FitFocus Team";
        String content = "<p>Dear " + fullUser.getUsername() + ", </p>";
        content += "<p> Please click on the link below to reset your password </p>";
        content += "<h3><a href=\"" + url + "\"> RESET PASSWORD </a></h3>";
        content += "<br><p>Ignore this mail if you did not make this request or remember your password<p>";
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

    public boolean verify(String token){
        User user = userRepository.findByPasswordResetToken(token);
        if (user == null) {
            return false;
        }
        return true;
    }

    public void sendVerificationEmailEmail(User user, String url) throws UnsupportedEncodingException, MessagingException {
        String subject = "FitFocus user verification";
        String senderName = "FitFocus Team";
        String content = "<p>Dear " + user.getUsername() + ", </p>";
        content += "<p> Please click on the link below to complete your email change: </p>";
        content += "<h3><a href=\"" + url + "\"> VERIFY </a></h3>";
        content += "<p> Thank you, <br> The FitFocus Team<p>";
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

    public boolean checkIfUserExistMail(String email) {
        return userRepository.findByEmail(email) != null ? true : false;
    }

    public Page<User> findPaginated(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize);
        return this.userRepository.findAll(pageable);
    }

    public Page<User> findByKeyword(String keyword, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber -1, pageSize);
        return userRepository.findByKeyword(keyword, pageable);
    }
}
