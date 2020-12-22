package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SessionController {
    @Autowired
    SessionService sessionService;

    @GetMapping("/sessions")
    public String getSessions(Model model){
        List<Session> sessionList = sessionService.getSessions();
        model.addAttribute("subTypes", sessionList);
        return "session";
    }

    @RequestMapping(value="/sessions/update", method= {RequestMethod.PUT, RequestMethod.GET})
    public String update(Session session) {
        sessionService.save(session);
        return "redirect:/session";
    }

    @PostMapping("/sessions/addNew")
    public String addNew(Session session){
        sessionService.save(session);
        return "redirect:/session";
    }

    @RequestMapping("/sessions/findById")
    @ResponseBody
    public Optional<Session> findById(int id){
        return sessionService.findById(id);
    }

    @RequestMapping(value = "/sessions/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id){
        sessionService.delete(id);
        return "redirect:/session";
    }
}
