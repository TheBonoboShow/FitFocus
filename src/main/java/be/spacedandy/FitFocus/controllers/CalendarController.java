package be.spacedandy.FitFocus.controllers;

import be.spacedandy.FitFocus.models.Event;
import be.spacedandy.FitFocus.models.Session;
import be.spacedandy.FitFocus.services.SessionService;
import flexjson.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CalendarController {
    @Autowired
    SessionService sessionService;

    @RequestMapping(value = "/calendar", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResponseEntity<String> getPlanificado()
    {
        List<Event> events = new ArrayList<>();
        List<Session> sessions = sessionService.getSessions();
        return getAllSessions(sessions, events);
    }

    @RequestMapping(value = "/calendarYoga", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResponseEntity<String> getPlanificado1()
    {
        List<Event> events = new ArrayList<>();
        List<Session> sessions = sessionService.getSessionsYoga();
        return getAllSessions(sessions, events);
    }

    @RequestMapping(value = "/calendarBoxing", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResponseEntity<String> getPlanificado2()
    {
        List<Event> events = new ArrayList<>();
        List<Session> sessions = sessionService.getSessionsBoxing();
        return getAllSessions(sessions, events);
    }

    @RequestMapping(value = "/calendarCycling", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResponseEntity<String> getPlanificado3()
    {
        List<Event> events = new ArrayList<>();
        List<Session> sessions = sessionService.getSessionsCycling();
        return getAllSessions(sessions, events);
    }

    @RequestMapping(value = "/calendarAerobic", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResponseEntity<String> getPlanificado4()
    {
        List<Event> events = new ArrayList<>();
        List<Session> sessions = sessionService.getSessionsAerobic();
        return getAllSessions(sessions, events);
    }

    @RequestMapping(value = "/calendarCardio", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public ResponseEntity<String> getPlanificado5()
    {
        List<Event> events = new ArrayList<>();
        List<Session> sessions = sessionService.getSessionsCardio();
        return getAllSessions(sessions, events);
    }



    private ResponseEntity<String> getAllSessions(List<Session> sessions, List<Event> events){
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
}
