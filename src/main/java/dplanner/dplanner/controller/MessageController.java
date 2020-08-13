package dplanner.dplanner.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dplanner.dplanner.dto.MessageDto;
import dplanner.dplanner.service.MessageService;

@RestController
@RequestMapping("/events")
//@CrossOrigin(origins = { "http://localhost:8081" })
@CrossOrigin(origins = { "https://mercury10-2.github.io/DailyPlannerClient/" })
public class MessageController {
    
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
        service.generateData();
    }

    @GetMapping
    public MessageDto getEmptyMessage() {
        return new MessageDto();
    }
    
    @GetMapping("/{query}")
    public List<MessageDto> getMessages(@PathVariable(name = "query") String query) {
        return service.getMessages(query);
    }

    @PutMapping("/{id}/{query}")
    public List<MessageDto> markDone(@PathVariable(name = "id") long id, @PathVariable(name = "query") String query) {
        return service.markDone(id, query);
    }

    @PostMapping("/{header}/{comment}/{date}/{month}/{year}/{hour}/{minute}/{query}")
    public MessageDto createEntry(  @PathVariable(name = "header") String header,
                                    @PathVariable(name = "comment") String comment,
                                    @PathVariable(name = "date") int date,
                                    @PathVariable(name = "month") int month,
                                    @PathVariable(name = "year") int year,
                                    @PathVariable(name = "hour") int hour,
                                    @PathVariable(name = "minute") int minute,
                                    @PathVariable(name = "query") String query) {
        return service.createEntry(header, comment, date, month, year, hour, minute, query);
    }

    @PutMapping("/{id}/{header}/{comment}/{date}/{month}/{year}/{hour}/{minute}/{query}")
    public List<MessageDto> editEntry(  @PathVariable(name = "id") long id,
                                        @PathVariable(name = "header") String header,
                                        @PathVariable(name = "comment") String comment,
                                        @PathVariable(name = "date") int date,
                                        @PathVariable(name = "month") int month,
                                        @PathVariable(name = "year") int year,
                                        @PathVariable(name = "hour") int hour,
                                        @PathVariable(name = "minute") int minute,
                                        @PathVariable(name = "query") String query) {
        return service.editEntry(id, header, comment, date, month, year, hour, minute, query);
    }

    @DeleteMapping("/{id}/{query}")
    public List<MessageDto> deleteEntry(@PathVariable(name = "id") long id, @PathVariable(name = "query") String query) {
        return service.deleteEntry(id, query);
    }

    @DeleteMapping()
    public void deleteAll() {
        service.deleteAll();
    }
}