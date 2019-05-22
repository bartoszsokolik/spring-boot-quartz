package pl.solutions.software.sokolik.bartosz.email;

import lombok.AllArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;
import pl.solutions.software.sokolik.bartosz.email.dto.ScheduleEmailRequest;
import pl.solutions.software.sokolik.bartosz.email.dto.ScheduleEmailResponse;
import pl.solutions.software.sokolik.bartosz.email.exception.InvalidEmailDateException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailJobSchedulerFacade {

    private final Scheduler scheduler;

    public ScheduleEmailResponse scheduleEmail(ScheduleEmailRequest request) {

        if (request.getDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidEmailDateException("Request date is earlier then now");
        }

        JobDetail jobDetail = buildJobDetail(request);
        Trigger trigger = buildJobTrigger(jobDetail, request.getDateTime());
        return scheduleJob(jobDetail, trigger);
    }

    private JobDetail buildJobDetail(ScheduleEmailRequest request) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("email", request.getEmail());
        jobDataMap.put("subject", request.getSubject());
        jobDataMap.put("body", request.getBody());

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, LocalDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant(ZoneOffset.UTC)))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

    private ScheduleEmailResponse scheduleJob(JobDetail jobDetail, Trigger trigger) {
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            return createResponse(jobDetail);
        } catch (SchedulerException e) {
            throw new RuntimeException();
        }
    }

    private ScheduleEmailResponse createResponse(JobDetail jobDetail) {
        return ScheduleEmailResponse.builder()
                .success(true)
                .jobId(jobDetail.getKey().getName())
                .jobGroup(jobDetail.getKey().getGroup())
                .message("Email Scheduled Successfully")
                .build();
    }
}
