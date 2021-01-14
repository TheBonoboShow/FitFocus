package be.spacedandy.FitFocus.services;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderEmailService {

    @Autowired JavaMailSender javaMailSender;
    @Autowired SessionRepository sessionRepository;

    public List<Session> getAllSessionsTomorrow() {
        return sessionRepository.findAllByDate(LocalDate.now().plusDays(1).toString()).stream().collect(Collectors.toList());
    }

    public void sendReminderEmail() throws UnsupportedEncodingException, MessagingException {
        List<Session> sessions = getAllSessionsTomorrow();

        for (Session s: sessions){
            List<User> users = s.getParticipants();
            for (User u : users){
                String subject = "Reminder booked session @FitFocus tomorrow";
                String senderName = "FitFocus";
                String content = "<p>Hello " + u.getUsername() + " ,</p><br>";
                content += "<p>We would like to remind you of a booked session at our fitness<p><br>";
                content += "<p>Details about the session:</p>";
                content += "<p>Date: " + s.getDate()+ "</p>";
                content += "<p>Information: " + s.getInformation()+ "</p>";
                content += "<p>Starting time: " + s.getStartingHour()+ "</p>";
                content += "<p>End time: " + s.getEndHour()+ "</p>";
                content += "<p>Sport: " + s.getSport()+ "</p>";
                content += "<br><p>The FitFocus Team<p>";

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
}
