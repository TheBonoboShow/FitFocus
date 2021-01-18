package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.Sport;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.models.UserPrincipal;
import be.spacedandy.FitFocus.security.NoSessionsLeftException;
import be.spacedandy.FitFocus.security.NoValidSubscriptionException;
import be.spacedandy.FitFocus.security.SessionOverlapException;
import be.spacedandy.FitFocus.services.SessionService;
import be.spacedandy.FitFocus.services.SportService;
import be.spacedandy.FitFocus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
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
        List<Session> sessionList = sessionService.getFutureSessions();
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
    public String update(Session session) throws SessionOverlapException {
        sessionService.save(session);
        return "redirect:/sessions";
    }

    @PostMapping("/sessions/addNew")
    public String addNew(Session session, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        try{
        sessionService.save(session);
        }
        catch (SessionOverlapException e){
            model.addAttribute("message","Sessions cannot overlap, please pick another timeslot");
            List<Session> sessionList = sessionService.getFutureSessions();
            model.addAttribute("sessions", sessionList);
            List<Sport> sportsList = sportService.getSports();
            model.addAttribute("sports", sportsList);
            List<User> userList = userService.getUsers();
            model.addAttribute("users", userList);
            User user = userService.findByUsername(userPrincipal.getUsername());
            model.addAttribute("user", user);
            return "session";
        }
        return "redirect:/sessions";
    }

    @RequestMapping("/sessions/findById")
    @ResponseBody
    public Session findById(int id){
        return sessionService.findById(id);
    }

    @RequestMapping(value = "/sessions/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id) throws UnsupportedEncodingException, MessagingException {
        sessionService.delete(id);
        return "redirect:/sessions";
    }

    @GetMapping("/bookSession")
    public String bookSession(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model){
        List<Sport> sportsList = sportService.getSports();
        model.addAttribute("sports", sportsList);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            model.addAttribute("message", "You need to be logged in to book a session");
            return "login";
        }
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        List<Session> sessionList = sessionService.getUserSessions(user);
        model.addAttribute("sessions", sessionList);
        List<Session> sessionList2 = sessionService.getNonBookedSessions(user);
        model.addAttribute("sessionsFuture", sessionList2);
        return "bookSession";
    }

    @PostMapping("/bookSessionX")
    public String bookSession(Session session, @AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userService.findByUsername(userPrincipal.getUsername());
        try {
            userService.addSessionToUser(session, user);
        }
        catch (NoSessionsLeftException e){
            model.addAttribute("message","You don't have any sessions left, consider upgrading your subscription type");
            model.addAttribute("user", user);
            List<Session> sessionList = sessionService.getUserSessions(user);
            model.addAttribute("sessions", sessionList);
            List<Session> sessionList2 = sessionService.getNonBookedSessions(user);
            model.addAttribute("sessionsFuture", sessionList2);
            return "bookSession";
        }
        catch (NoValidSubscriptionException e){
            model.addAttribute("message","You don't have a valid subscription at the moment, consider buying one");
            model.addAttribute("user", user);
            List<Session> sessionList = sessionService.getUserSessions(user);
            model.addAttribute("sessions", sessionList);
            List<Session> sessionList2 = sessionService.getNonBookedSessions(user);
            model.addAttribute("sessionsFuture", sessionList2);
            return "bookSession";
        }
        return "redirect:/bookSession";
    }

    @RequestMapping(value = "/bookSession/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteSession(Integer id, @AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userService.findByUsername(userPrincipal.getUsername());
        sessionService.deleteSession(id, user);
        return "redirect:/bookSession";
    }

    @GetMapping("/sessions/past")
    public String getSessionsPast(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        List<Session> sessionList = sessionService.getPastSessions();
        model.addAttribute("sessions", sessionList);
        List<Sport> sportsList = sportService.getSports();
        model.addAttribute("sports", sportsList);
        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        return "session_past";
    }
}
