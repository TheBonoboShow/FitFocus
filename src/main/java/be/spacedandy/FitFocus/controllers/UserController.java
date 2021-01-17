package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.models.UserPrincipal;
import be.spacedandy.FitFocus.security.EmailAlreadyExistException;
import be.spacedandy.FitFocus.security.UserNotFoundException;
import be.spacedandy.FitFocus.security.WrongPasswordException;
import be.spacedandy.FitFocus.services.SessionService;
import be.spacedandy.FitFocus.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    SessionService sessionService;

    @GetMapping("/profile")
    public String getUser(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/update")
    public String getUpdateForm(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        return "profile_edit";
    }

    @PostMapping(value = "/profile/update")
    public String update(User user, BindingResult bindingResult, Model model) {
        try {
            userService.update(user);
        } catch (WrongPasswordException e) {
            bindingResult.rejectValue("password", "user.password", "The password is not correct");
            model.addAttribute("user", user);
            return "profile_edit";
        }
        return "redirect:/profile";
    }


    @RequestMapping("/profile/findById")
    @ResponseBody
    public User findById(int id) {
        return userService.findById(id);
    }

    @RequestMapping("/profile/findByUsername")
    @ResponseBody
    public User findByUsername(String string) {
        return userService.findByUsername(string);
    }

    @RequestMapping("/profile/findByEmail")
    @ResponseBody
    public User findByEmail(String email) {
        return userService.findByEmail(email);
    }

    @RequestMapping(value = "/profile/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/profile/updateEmail")
    public String updateEmailForm(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        return "update_email_form";
    }

    @PostMapping("/profile/updateEmail")
    public String sendEmailUpdateEmail(@AuthenticationPrincipal UserPrincipal userPrincipal, User user, BindingResult bindingResult, Model model) throws InterruptedException {
        try {
            if (user.getEmail().equals("")) throw new UserNotFoundException("");
            if (userService.checkIfUserExistMail(user.getEmail())) throw new EmailAlreadyExistException("");
            if (!userService.checkIfPasswordMatches(user)) throw new WrongPasswordException("");

            String token = RandomString.make(32);
            String url = "http://localhost:8080/verify?token=";
            url += token;
            User userP = userService.findByUsername(userPrincipal.getUsername());
            userP.setEmail(user.getEmail());
            userP.setVerificationToken(token);
            userP.setProfileIsActive(false);
            userService.saveAdmin(userP);
            model.addAttribute("user", userP);
            userService.sendVerificationEmailEmail(userP, url);
        } catch (UserNotFoundException e) {
            bindingResult.rejectValue("email", "user.email", "Please enter a valid email address");
            model.addAttribute("user", user);
            return "update_email_form";
        } catch (EmailAlreadyExistException e) {
            bindingResult.rejectValue("email", "user.email", "This email already exists");
            model.addAttribute("user", user);
            return "update_email_form";
        } catch (WrongPasswordException e) {
            bindingResult.rejectValue("password", "user.password", "The password is not correct");
            model.addAttribute("user", user);
            return "update_email_form";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
        return "redirect:/logout";
    }
}
