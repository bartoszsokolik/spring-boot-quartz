package pl.solutions.software.sokolik.bartosz.email.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ApiError {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private String url;
}
