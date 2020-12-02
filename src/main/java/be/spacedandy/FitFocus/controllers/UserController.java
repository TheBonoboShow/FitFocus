package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired UserService userService;

    @GetMapping("/users")
    public String getUser(){
        return "index";
    }

    @RequestMapping(value="/users/update", method= {RequestMethod.PUT, RequestMethod.GET})
    public String update(User user) {
        userService.save(user);
        return "redirect:/users";
    }
}
