package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Email;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
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
            content += "<img src='cid:logo' style='height: 65px; width: auto'/>";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("ibo@staes.me", senderName);
            helper.setTo(u.getEmail());
            helper.setSubject(subject);
            FileSystemResource image = new FileSystemResource(new File("src/main/resources/static/img/logo.jpg"));
            helper.setText(content, true);
            helper.addInline("logo", image);

            javaMailSender.send(message);
        }
    }
}
