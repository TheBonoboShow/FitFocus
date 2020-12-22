package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired UserService userService;

    @GetMapping("/profile")
    public String getUser(Model model){
        Optional<User> user = userService.findById(1); //todo (needs to be current user)
        model.addAttribute("profile", user);
        return "profile";
    }

    @RequestMapping(value = "/profile/update", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(User user){
        userService.save(user);
        return "redirect:/profile";
    }
}
