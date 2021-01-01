package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.*;
import be.spacedandy.FitFocus.security.WrongPasswordException;
import be.spacedandy.FitFocus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired UserService userService;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/profile")
    public String getUser(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model){
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping( "/profile/update")
    public String getUpdateForm(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model){
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        return "profile_edit";
    }

    @PostMapping(value = "/profile/update")
    public String update(User user, BindingResult bindingResult, Model model) {
        try {
            userService.update(user);
        }catch (WrongPasswordException e){
            bindingResult.rejectValue("password", "user.password","The password is not correct");
            model.addAttribute("user", user);
            return "profile_edit";
        }
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
