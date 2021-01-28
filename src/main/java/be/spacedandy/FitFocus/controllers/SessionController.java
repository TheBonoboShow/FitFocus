package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.models.Sport;
import be.spacedandy.FitFocus.models.User;
import be.spacedandy.FitFocus.models.UserPrincipal;
import be.spacedandy.FitFocus.security.*;
import be.spacedandy.FitFocus.services.SessionService;
import be.spacedandy.FitFocus.services.SportService;
import be.spacedandy.FitFocus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class SessionController {
    @Autowired
    SessionService sessionService;
    @Autowired
    SportService sportService;
    @Autowired
    UserService userService;

    @GetMapping("/sessions")
    public String getSessions(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal, String startDate, String endDate){
        return findPaginatedSession(1, model, startDate, endDate, userPrincipal);
    }

    @GetMapping("/pages/{pageNumber}")
    public String findPaginatedSession(@PathVariable(value = "pageNumber") int pageNumber, Model model, String startDate, String endDate, @AuthenticationPrincipal UserPrincipal userPrincipal){
        int pageSize = 15;
        Page<Session> page;

        if (startDate != null) {
            page = sessionService.findByDateRange(startDate, endDate, pageNumber, pageSize);
        } else {
            page = sessionService.findPaginated(pageNumber, pageSize);
        }

        List<Session> sessions = page.getContent();
        model.addAttribute("currentPage" , pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sessions", sessions);
        List<Sport> sportsList = sportService.getSports();
        model.addAttribute("sports", sportsList);
        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "session";
    }

    @RequestMapping(value="/sessions/update", method= {RequestMethod.PUT, RequestMethod.GET})
    public String update(Session session) throws SessionOverlapException {
        sessionService.update(session);
        return "redirect:/sessions";
    }

    @PostMapping("/sessions/addNew")
    public String addNew(Session session, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        try{
        sessionService.save(session);
        }
        catch (SessionOverlapException e){
            model.addAttribute("message","Sessions cannot overlap, please pick another timeslot");
            return findPaginatedSession(1, model, null, null, userPrincipal);

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
    public String bookSession(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model, @RequestParam int id){
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
        model.addAttribute("sessionToBook", sessionService.findById(id));
        return "bookSession";
    }


    @PostMapping("/bookSessionX")
    public String bookSession(Session session, @AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        User user = userService.findByUsername(userPrincipal.getUsername());
        model.addAttribute("user", user);
        List<Session> sessionList = sessionService.getUserSessions(user);
        model.addAttribute("sessions", sessionList);
        List<Session> sessionList2 = sessionService.getNonBookedSessions(user);
        model.addAttribute("sessionsFuture", sessionList2);
        model.addAttribute("sports", sportService.getSports());
        try {
            userService.addSessionToUser(session, user);
        }
        catch (NoSessionsLeftException e){
            model.addAttribute("message","You don't have any sessions left, consider upgrading your subscription type");
            return "bookSession";
        }
        catch (NoValidSubscriptionException e){
            model.addAttribute("message","You don't have a valid subscription at the moment, consider buying one");
            return "bookSession";
        }
        catch (NotFemaleException e){
            model.addAttribute("message","You can't book female only sessions");
            return "bookSession";
        }
        catch (SessionInPastException e){
            model.addAttribute("message","You can only book future sessions");
            return "bookSession";
        }
        return "redirect:/bookSession?id=0";
    }

    @RequestMapping(value = "/bookSession/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteSession(Integer id, @AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userService.findByUsername(userPrincipal.getUsername());
        sessionService.deleteSession(id, user);
        return "redirect:/bookSession?id=0";
    }

    @GetMapping("/sessions/past")
    public String getSessionsPast(Model model, String startDate, String endDate){
        return findPaginatedSessionPast(1, model, startDate, endDate);
    }

    @GetMapping("/pageS/{pageNumber}")
    public String findPaginatedSessionPast(@PathVariable(value = "pageNumber") int pageNumber, Model model, String startDate, String endDate){
        int pageSize = 15;
        Page<Session> page;
        if (startDate != null) {
            page = sessionService.findByDateRangePast(startDate, endDate, pageNumber, pageSize);
        } else {
            page = sessionService.findPaginatedPast(pageNumber, pageSize);
        }
        List<Session> sessions = page.getContent();

        model.addAttribute("currentPage" , pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sessions", sessions);
        List<Sport> sportsList = sportService.getSports();
        model.addAttribute("sports", sportsList);
        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "session_past";
    }
}
