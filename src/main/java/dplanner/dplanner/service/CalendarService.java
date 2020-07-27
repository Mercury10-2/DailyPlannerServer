package dplanner.dplanner.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dplanner.dplanner.domain.Message;
import dplanner.dplanner.dto.Event;

@Service
public class CalendarService {

    private final MessageService messageService;

    public CalendarService(MessageService messageService) {
        this.messageService = messageService;
    }

    public List<Event> getEvents() {
        return messageService.getEvents().stream()
                .map(message -> changeToEvent(message))
                .collect(Collectors.toList());
    }

    private Event changeToEvent(Message message) {
        return new Event(message.getId(), message.getHeader(), message.getComments(),
                dateToString(message.getPlannedTime()), dateToString(message.getPlannedTime()));
    }

    private String dateToString(ZonedDateTime date) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(date).toString();
    }
}