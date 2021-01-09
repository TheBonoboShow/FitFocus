package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.Email;
import be.spacedandy.FitFocus.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
public class EmailController {
    @Autowired EmailService emailService;

    @GetMapping("/admin/sendPromo")
    public String getPromoForm(Model model){
        Email email = new Email();
        model.addAttribute("email", email);
        return "admin_send_promotions";
    }

    @PostMapping("/admin/sendPromo")
    public String sendPromoEmail(Email email, Model model) throws UnsupportedEncodingException, MessagingException{
        emailService.sendPromoEmail(email);
        model.addAttribute("message", "The promo email was sent");
        return "admin_send_promotions";
    }
}
