package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Email;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {
    @Autowired UserRepository userRepository;
    @Autowired JavaMailSender javaMailSender;

    public List<User> getAllUsersWithPromo(){
        return userRepository.findAll().stream().filter(u -> u.isPromotionsActive() == true).collect(Collectors.toList());
    }

    public void sendPromoEmail(Email email) throws UnsupportedEncodingException, MessagingException  {
        List<User> users = getAllUsersWithPromo();

        for (User u : users){
            String subject = email.getSubject();
            String senderName = "FitFocus Promo Team";
            String content = "<p>Hello " + u.getUsername() + " ,</p>";
            content += email.getMessage() + "<br>";
            content += "<br><p>The FitFocus Promo Team<p>";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("ibo@staes.me", senderName);
            helper.setTo(u.getEmail());
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
        }
    }
}
