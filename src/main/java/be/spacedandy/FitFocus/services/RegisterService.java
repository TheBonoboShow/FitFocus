package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.security.EmailAlreadyExistException;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.security.UserAlreadyExistException;
import be.spacedandy.FitFocus.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class RegisterService {
    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private JavaMailSender javaMailSender;

    public User register(User user) throws UserAlreadyExistException, EmailAlreadyExistException {

        if (checkIfUserExistName(user.getUsername())) {
            throw new UserAlreadyExistException("This user already has a registered account");
        }
        if (checkIfUserExistMail(user.getEmail())) {
            throw new EmailAlreadyExistException("User already exists for this email");
        }
        user.setVerificationToken(RandomString.make(32));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }



    public boolean checkIfUserExistMail(String email) {
        return userRepository.findByEmail(email) != null ? true : false;
    }

    public boolean checkIfUserExistName(String name) {
        return userRepository.findByUsername(name) != null ? true : false;
    }

    public void sendVerificationEmail(User user, String url) throws UnsupportedEncodingException, MessagingException {
        String subject = "FitFocus user verification";
        String senderName = "FitFocus Team";
        String content = "<p>Dear " + user.getUsername() + ", </p>";
        content += "<p> Please click on the link below to complete your account registration: </p>";
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

    public boolean verify(String code){
        User user = userRepository.findByVerificationToken(code);
        if (user == null || user.isProfileIsActive()) {
            return false;
        }
        user.setProfileIsActive(true);
        user.setVerificationToken(null);
        userRepository.save(user);
        return true;
    }
}
