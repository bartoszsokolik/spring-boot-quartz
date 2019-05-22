package pl.solutions.software.sokolik.bartosz.email;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.solutions.software.sokolik.bartosz.email.dto.ScheduleEmailRequest;
import pl.solutions.software.sokolik.bartosz.email.dto.ScheduleEmailResponse;

@RestController
@RequestMapping("/api/mail")
@AllArgsConstructor
class EmailJobSchedulerController {

    private final EmailJobSchedulerFacade emailJobSchedulerFacade;

    @PostMapping("/schedule")
    ResponseEntity<ScheduleEmailResponse> scheduleEmail(@RequestBody ScheduleEmailRequest request) {
        ScheduleEmailResponse response = emailJobSchedulerFacade.scheduleEmail(request);
        return ResponseEntity.ok(response);
    }

}
