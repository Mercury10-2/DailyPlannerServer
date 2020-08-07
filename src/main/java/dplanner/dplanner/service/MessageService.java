package dplanner.dplanner.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dplanner.dplanner.domain.Message;
import dplanner.dplanner.dto.MessageDto;
import dplanner.dplanner.repository.MessageRepository;

@Service
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final static Comparator<Message> messageComparator = Comparator.comparing(Message::isCompleted).reversed()
                                            .thenComparing(Message::getCompletionTime)
                                            .thenComparing(Message::getPlannedTime)
                                            .thenComparing(Message::getCreated);

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void generateData() {
        if (messageRepository.findAll().isEmpty()) {
            messageRepository.save(new Message("Сходить в магазин", "Список стандартный", ZonedDateTime.now().plusDays(3), TimeService.DEFAULT));
            messageRepository.save(new Message("Помыть посуду", "", ZonedDateTime.now().plusDays(2), TimeService.DEFAULT));
            messageRepository.save(new Message("Сделать упражнения", "", ZonedDateTime.now().plusDays(1), TimeService.DEFAULT));
            messageRepository.save(new Message("Помедитировать", "Дома и правители", ZonedDateTime.now().plusDays(1), TimeService.DEFAULT));
            messageRepository.save(new Message("Съездить в отпуск", "Море, море, море", ZonedDateTime.now().plusMonths(2), TimeService.DEFAULT));
            messageRepository.save(new Message("Сходить в гости", "ДР у начальника", ZonedDateTime.now().plusWeeks(1), TimeService.DEFAULT));
            messageRepository.save(new Message("Переехать", "В глушь, в Саратов", ZonedDateTime.now().plusYears(2), TimeService.DEFAULT));
            Message msg = new Message("Поесть", "", ZonedDateTime.now().plusDays(1), ZonedDateTime.now());
            msg.setCompleted(true);
            messageRepository.save(msg);
        }
    }

    public List<MessageDto> getMessages(String query) {
        if (query.equals("all-time") || query.equals("calendar") || query.equals("weather"))
            return TimeService.changeToDtoList(sortMessages(getAllMessages()));
        boolean completed = query.equals("actual") ? false : true;
        return TimeService.changeToDtoList(sortMessages(getAllMessages()).stream()
                .filter(x -> x.isCompleted() == completed)
                .collect(Collectors.toList()));
    }

    public List<MessageDto> markDone(long id, String query) {
        Message message = messageRepository.findById(id).get();
        message.setCompleted(!message.isCompleted());
        if (message.isCompleted()) {
            message.setCompletionTime(ZonedDateTime.now());
            message.setPlannedTime(message.getCompletionTime());
        } else {
            message.setCompletionTime(TimeService.DEFAULT);
            message.setPlannedTime(message.getPrevPlannedTime());
        }
        messageRepository.save(message);
        return getMessages(query);
    }

    public MessageDto createEntry(String header, String comment, int date, int month,
                                        int year, int hour, int minute, String query) {
        ZonedDateTime plannedTime = ZonedDateTime.of(year, month, date, hour, minute, 0, 0, ZoneId.systemDefault());
        return TimeService.changeToDto(messageRepository.save(new Message(header, comment, plannedTime, plannedTime)));
    }
/*
    public List<MessageDto> editEvent(String data) {
        Map<String,String> event = new HashMap<String,String>();
        ObjectMapper mapper = new ObjectMapper();
        event = mapper.readValue(data, HashMap.class);
        Message message = messageRepository.findById(Long.parseLong(event.get("id"))).get();
        message.setHeader(event.get("header").toString());
        message.setComments(event.get("comment").toString());
        if (!message.isCompleted())
            message.setPlannedTime(ZonedDateTime.of(Integer.parseInt(event.get("year")), Integer.parseInt(event.get("month")), Integer.parseInt(event.get("date")), Integer.parseInt(event.get("hour")), Integer.parseInt(event.get("minute")), 0, 0, ZoneId.systemDefault()));
        else
            message.setCompletionTime(ZonedDateTime.of(Integer.parseInt(event.get("year")), Integer.parseInt(event.get("month")), Integer.parseInt(event.get("date")), Integer.parseInt(event.get("hour")), Integer.parseInt(event.get("minute")), 0, 0, ZoneId.systemDefault()));
        messageRepository.save(message);
        return getMessages(event.get("query").toString());
        Message message = messageRepository.findById(event.get()).get();
        message.setHeader(event.getHeader());
        message.setComments(event.getComment());
        if (!message.isCompleted())
            message.setPlannedTime(ZonedDateTime.of(event.getYear(), event.getMonth(), event.getDate(), event.getHour(), event.getMinute(), 0, 0, ZoneId.systemDefault()));
        else
            message.setCompletionTime(ZonedDateTime.of(event.getYear(), event.getMonth(), event.getDate(), event.getHour(), event.getMinute(), 0, 0, ZoneId.systemDefault()));
        messageRepository.save(message);
        return getMessages(event.getQuery());
    }*/

    public List<MessageDto> editEntry(long id, String header, String comment, int date, int month,
                                        int year, int hour, int minute, String query) {
        Message message = messageRepository.findById(id).get();
        message.setHeader(header);
        message.setComments(comment);
        if (!message.isCompleted())
            message.setPlannedTime(ZonedDateTime.of(year, month, date, hour, minute, 0, 0, ZoneId.systemDefault()));
        else
            message.setCompletionTime(ZonedDateTime.of(year, month, date, hour, minute, 0, 0, ZoneId.systemDefault()));
        messageRepository.save(message);
        return getMessages(query);
    }

    public List<MessageDto> deleteEntry(long id, String query) {
        messageRepository.deleteById(id);
        return getMessages(query);
    }

    public void deleteAll() {
        messageRepository.deleteAll();
    }

    private List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    private List<Message> sortMessages(List<Message> messages) {
        return messages.stream()
                .sorted(messageComparator)
                .collect(Collectors.toList());
    }

    public List<Message> getEvents() {
        return sortMessages(getAllMessages());
    }
}