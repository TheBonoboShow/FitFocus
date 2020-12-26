package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.EmailAlreadyExistException;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.models.UserAlreadyExistException;
import be.spacedandy.FitFocus.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String userRegistration(final @Valid User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "register_form";
        }
        try {
            registerService.register(user);
        }catch (UserAlreadyExistException e){
            bindingResult.rejectValue("username", "user.username","An account already exists for this name.");
            model.addAttribute("user", user);
            return "register_form";
        } catch (EmailAlreadyExistException e){
            bindingResult.rejectValue("email", "user.email","An account already exists for this email.");
            model.addAttribute("user", user);
            return "register_form";
        }
        return "index";
    }
}
