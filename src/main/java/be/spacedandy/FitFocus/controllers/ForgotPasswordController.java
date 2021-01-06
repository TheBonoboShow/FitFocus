package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.security.UserNotFoundException;
import be.spacedandy.FitFocus.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @GetMapping ("/secret")
    public String showSecretForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
        User user = new User();
        model.addAttribute("user", user);
        return "secret";
        }
        return "redirect:/index";
    }

    @PostMapping("/secret")
    public String sendMailResetPassword(User user, BindingResult bindingResult, Model model){
        try {
            String token = RandomString.make(32);
            userService.resetPassword(token,user.getEmail());
            String url = "http://localhost:8080/verifysecret?token=" ;
            url += token;
            userService.sendVerificationEmail(user, url);
            model.addAttribute("message", "We have sent you an email, please check");
        }
        catch (UserNotFoundException e){
            bindingResult.rejectValue("email", "user.email","Could not find an account with this email: " + user.getEmail());
            model.addAttribute("user", user);
            return "secret";
        }
        catch (UnsupportedEncodingException | MessagingException e){
            bindingResult.rejectValue("email", "user.email","An error occurred while sending the mail, please try again");
            model.addAttribute("user", user);
            return "secret";
        }
        return "secret";
    }

    @GetMapping("/verifysecret")
    public String resetPasswordForm(@Param("token") String token, Model model){
        boolean verified = userService.verify(token);
        model.addAttribute("token", token);
        return verified ? "secret_form" : "secret_error";
    }

    @PostMapping("/verifysecret")
    public String confirmResetPassword(User user, Model model) throws InterruptedException {
        userService.updatePassword(user);
        model.addAttribute("token", user.getPasswordResetToken());
        Thread.sleep(2500);
        return "redirect:/login";
    }
}
