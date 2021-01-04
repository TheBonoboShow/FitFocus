package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.security.UserNotFoundException;
import be.spacedandy.FitFocus.security.WrongPasswordException;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
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

    public void resetPassword (String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null){
            user.setPasswordResetToken(token);
            userRepository.save(user);
        } else throw new UserNotFoundException("Could not find an account with this email: " + email);
    }

    public void updatePassword (User user){
        User oldUser = userRepository.findByPasswordResetToken(user.getPasswordResetToken());
        System.out.println("EPIC TEST " + user);
        System.out.println("EPIC TEST " + oldUser);
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

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("ibo@staes.me", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    public boolean verify(String token){
        User user = userRepository.findByPasswordResetToken(token);
        if (user == null) {
            return false;
        }
        return true;
    }
}
