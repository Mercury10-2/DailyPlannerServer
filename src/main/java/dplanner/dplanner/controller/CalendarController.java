package dplanner.dplanner.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dplanner.dplanner.dto.Event;
import dplanner.dplanner.service.CalendarService;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = { "http://localhost:8081" })
public class CalendarController {
    
    private final CalendarService service;

    public CalendarController(CalendarService service) {
        this.service = service;
    }

    @GetMapping
    public List<Event> getCalendar() {
        return service.getEvents();
    }
}