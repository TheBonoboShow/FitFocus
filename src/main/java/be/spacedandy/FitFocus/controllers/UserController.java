package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired UserService userService;

    @GetMapping("/profile")
    public String getUser(){
        return "profile";
    }

    @RequestMapping(value = "/profile/update", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update(User user) {
        userService.save(user);
        return "redirect:/profile";
    }

    @RequestMapping("/profile/findById")
    @ResponseBody
    public User findById(int id){
        return userService.findById(id);
    }

    @RequestMapping("/profile/findByUsername")
    @ResponseBody
    public User findByUsername(String string){
        return userService.findByUsername(string);
    }

    @RequestMapping("/profile/findByEmail")
    @ResponseBody
    public User findByEmail(String email){
        return userService.findByEmail(email);
    }

    @RequestMapping(value = "/profile/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
