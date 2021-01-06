package be.spacedandy.FitFocus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

        MimeMessage message1 = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message1);
        helper.setFrom("ibo@staes.me", senderName);
        helper.setTo("fitfocusteam@gmail.com");
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message1);
    }
}
