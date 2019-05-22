package pl.solutions.software.sokolik.bartosz.email.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ScheduleEmailRequest {

    private String email;

    private String subject;

    private String body;

    private LocalDateTime dateTime;
}
