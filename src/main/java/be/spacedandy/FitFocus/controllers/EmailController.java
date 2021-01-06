package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailController {
    @Autowired EmailService emailService;

    @GetMapping("/admin/sendPromo")
    public String sendPromoEmail(Model Model){
        emailService.getAllUsersWithPromo().forEach(System.out::println);
        return "admin_send_promotions";
    }
}
