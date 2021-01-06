package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.Email;
import be.spacedandy.FitFocus.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/contact")
    public String getContactForm(Model model){
        Email email = new Email();
        model.addAttribute("email", email);
        return "contact";
    }

    @PostMapping("/contact")
    public String sendContactFormEmail(Email email, Model model) throws UnsupportedEncodingException, MessagingException {
        contactService.sendContactForm(email.getEmail(), email.getMessage());
        model.addAttribute("message", "We received your message correctly, thank you!");
        return "contact";
    }

}
