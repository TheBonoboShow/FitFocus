package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.security.EmailAlreadyExistException;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.security.UserAlreadyExistException;
import be.spacedandy.FitFocus.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register_form";
    }

    @PostMapping(value = "/register")
    public String userRegistration(final @Valid User user, BindingResult bindingResult, Model model) throws InterruptedException {

        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "register_form";
        }
        try {
            registerService.register(user);
            String url = "https://fitfocus.herokuapp.com/verify?token=" ;
            url += user.getVerificationToken();
            registerService.sendVerificationEmail(user, url);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("username", "user.username","An account already exists for this name");
            model.addAttribute("user", user);
            return "register_form";
        } catch (EmailAlreadyExistException e){
            bindingResult.rejectValue("email", "user.email","An account already exists for this email");
            model.addAttribute("user", user);
            return "register_form";
        }
        catch (UnsupportedEncodingException | MessagingException e){
            bindingResult.rejectValue("email", "user.email","Something went wrong, please try again");
            model.addAttribute("user", user);
            return "register_form";
        }
        model.addAttribute("message", "To complete your registration, please check your mails");
        return "login";
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("token") String token){
        boolean verified = registerService.verify(token);
        return verified ? "register_success" : "register_error";
    }
}
