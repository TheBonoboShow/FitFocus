package be.spacedandy.FitFocus;

import be.spacedandy.FitFocus.models.Event;
import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.services.SessionService;
import flexjson.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {

    @GetMapping("/index")
    public String goHome() {
        return "index";
    }

    @Autowired
    SessionService sessionService;

    @GetMapping("/index2")
    public String goHome2() {
        return "index2";
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResponseEntity<String> getPlanificado()
    {
        List<Event> events = new ArrayList<>();
        List<Session> sessions = sessionService.getSessions();
        for (Session s : sessions){
            Event e = new Event();
            e.setEnd(s.getDate().concat("T").concat(s.getEndHour()));
            e.setStart(s.getDate().concat("T").concat(s.getStartingHour()));
            e.setTitle(s.getSport().getName());
            events.add(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (events.size() > 0)
        {
            return new ResponseEntity<String>(new JSONSerializer().include("title","end","start").exclude("*").serialize(events), headers, HttpStatus.OK);
        }
        return new ResponseEntity<String>(null, headers, HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

}
