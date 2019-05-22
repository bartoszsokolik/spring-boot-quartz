package pl.solutions.software.sokolik.bartosz.email.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ScheduleEmailResponse {

    private boolean success;

    private String jobId;

    private String jobGroup;

    private String message;
}
