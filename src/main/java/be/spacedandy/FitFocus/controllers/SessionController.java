package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.Sport;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.models.UserPrincipal;
import be.spacedandy.FitFocus.services.SessionService;
import be.spacedandy.FitFocus.services.SportService;
import be.spacedandy.FitFocus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SessionController {
    @Autowired
    SessionService sessionService;
    @Autowired
    SportService sportService;
    @Autowired
    UserService userService;

    @GetMapping("/sessions")
    public String getSessions(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        List<Session> sessionList = sessionService.getSessions();
        model.addAttribute("sessions", sessionList);
        List<Sport> sportsList = sportService.getSports();
        model.addAttribute("sports", sportsList);
        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        return "session";
    }

    @RequestMapping(value="/sessions/update", method= {RequestMethod.PUT, RequestMethod.GET})
    public String update(Session session) {
        sessionService.save(session);
        return "redirect:/sessions";
    }

    @PostMapping("/sessions/addNew")
    public String addNew(Session session){
        sessionService.save(session);
        return "redirect:/sessions";
    }

    @RequestMapping("/sessions/findById")
    @ResponseBody
    public Optional<Session> findById(int id){
        return sessionService.findById(id);
    }

    @RequestMapping(value = "/sessions/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id){
        sessionService.delete(id);
        return "redirect:/sessions";
    }
}
