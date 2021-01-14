package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.services.ReminderEmailService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Configuration
@EnableScheduling
@Controller
public class ReminderEmailController {

    @Autowired
    ReminderEmailService reminderEmailService;

    @Scheduled(cron = "0 0 20 * * ?")
    public void runSendMail() throws SchedulerException, UnsupportedEncodingException, MessagingException {
        reminderEmailService.sendReminderEmail();
    }
}