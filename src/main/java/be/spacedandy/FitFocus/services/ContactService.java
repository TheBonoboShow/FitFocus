package be.spacedandy.FitFocus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class ContactService {

    @Autowired private JavaMailSender javaMailSender;

    public void sendContactForm(String email, String message) throws UnsupportedEncodingException, MessagingException {
        String subject = "FitFocus Contact Form Message";
        String senderName = email;
        String content = "<p>Hello, </p>";
        content += "<p>The following message was left by: " + email + "</p><br>";
        content += message + "<br>";
        content += "<br><p>Send with the FitFocus contact form<p>";
        content += "<img src='cid:logo' style='height: 65px; width: auto'/>";

        MimeMessage message1 = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message1, true);
        helper.setFrom("ibo@staes.me", senderName);
        helper.setTo("fitfocusteam@gmail.com");
        helper.setSubject(subject);
        FileSystemResource image = new FileSystemResource(new File("src/main/resources/static/img/logo.jpg"));
        helper.setText(content, true);
        helper.addInline("logo", image);

        javaMailSender.send(message1);
    }
}
