package dplanner.dplanner.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dplanner.dplanner.domain.Message;
import dplanner.dplanner.dto.MessageDto;

@Service
public class TimeService {

    private static final StringBuilder sb = new StringBuilder();
    public static final ZonedDateTime DEFAULT = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
    
    public static List<MessageDto> changeToDtoList(List<Message> messages) {
        return messages.stream()
                .map(message -> changeToDto(message))
                .collect(Collectors.toList());
    }

    public static MessageDto changeToDto(Message message) {
        MessageDto dto = new MessageDto(message.getId(), message.getHeader(), message.getComments(), message.isCompleted());
        dto.setCreated(dateToString(message.getCreated()));
        dto.setCompletionTime(dateToString(message.getCompletionTime()));
        dto.setPlannedTime(dateToString(message.getPlannedTime()));
        return dto;
    }

    private static String dateToString(ZonedDateTime date) {
        sb.setLength(0);
        if (date.toLocalDate().equals(LocalDate.now()))
            return sb.append("Сегодня, ").append(DateTimeFormatter.ofPattern("HH:mm").format(date)).toString();
        else if (date.toLocalDate().equals(LocalDate.now().plusDays(1)))
            return sb.append("Завтра, ").append(DateTimeFormatter.ofPattern("HH:mm").format(date)).toString();
        else if (date.toLocalDate().equals(LocalDate.now().plusDays(2)))
            return sb.append("Послезавтра, ").append(DateTimeFormatter.ofPattern("HH:mm").format(date)).toString();
        else if (date.toLocalDate().equals(LocalDate.now().minusDays(1)))
            return sb.append("Вчера, ").append(DateTimeFormatter.ofPattern("HH:mm").format(date)).toString();
        else if (date.toLocalDate().equals(LocalDate.now().minusDays(2)))
            return sb.append("Позавчера, ").append(DateTimeFormatter.ofPattern("HH:mm").format(date)).toString();
        else if (Math.abs(Period.between(LocalDate.now(), date.toLocalDate()).toTotalMonths()) < 2)
            return DateTimeFormatter.ofPattern("dd.MM, HH:mm").format(date).toString();
        else if (Math.abs(Period.between(LocalDate.now(), date.toLocalDate()).toTotalMonths()) < 12)
            return DateTimeFormatter.ofPattern("dd.MM").format(date).toString();
        else return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date).toString();
    }
}