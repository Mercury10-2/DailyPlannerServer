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
            messageRepository.save(new Message("Магазин", "Список на работе", ZonedDateTime.now().plusDays(3), TimeService.DEFAULT));
            messageRepository.save(new Message("Встреча с Г.", "Не забыть с.", ZonedDateTime.now().plusDays(9), TimeService.DEFAULT));
            messageRepository.save(new Message("Закончить к.", "Взять т.", ZonedDateTime.now().plusDays(17), TimeService.DEFAULT));
            messageRepository.save(new Message("Гостиный Двор", "18:00", ZonedDateTime.now().plusDays(1), TimeService.DEFAULT));
            messageRepository.save(new Message("Отпуск", "10 дней", ZonedDateTime.now().plusMonths(2), TimeService.DEFAULT));
            messageRepository.save(new Message("ДР у начальника", "Дача", ZonedDateTime.now().plusWeeks(6), TimeService.DEFAULT));
            messageRepository.save(new Message("Переезд", "В глушь, в Саратов", ZonedDateTime.now().plusYears(2), TimeService.DEFAULT));
            Message msg = new Message("Забрать м.", "Позвонить заранее", ZonedDateTime.now().plusDays(5), ZonedDateTime.now());
            msg.setCompleted(true);
            messageRepository.save(msg);
            msg = new Message("Налоговая", "Не забыть ИНН", ZonedDateTime.now().plusDays(1), ZonedDateTime.now());
            msg.setCompleted(true);
            messageRepository.save(msg);
            msg = new Message("Оставить к.", "В пакете", ZonedDateTime.now().plusDays(2), ZonedDateTime.now());
            msg.setCompleted(true);
            messageRepository.save(msg);
            msg = new Message("Встреча с В.", "Позвонит", ZonedDateTime.now().plusDays(4), ZonedDateTime.now());
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
        return TimeService.changeToDto(messageRepository.save(new Message(header, comment, plannedTime, TimeService.DEFAULT)));
    }

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