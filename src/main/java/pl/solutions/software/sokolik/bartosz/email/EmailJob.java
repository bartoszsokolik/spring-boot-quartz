package pl.solutions.software.sokolik.bartosz.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@AllArgsConstructor
class EmailJob extends QuartzJobBean {

    private final JavaMailSender mailSender;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String subject = dataMap.getString("subject");
        String body = dataMap.getString("body");
        String recipientEmail = dataMap.getString("email");

        sendMail(recipientEmail, subject, body);
    }

    private void sendMail(String recipient, String subject, String body) {
        try {
            log.info("Sending email to {}", recipient);
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body);
            messageHelper.setTo(recipient);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email to");
        }
    }
}
