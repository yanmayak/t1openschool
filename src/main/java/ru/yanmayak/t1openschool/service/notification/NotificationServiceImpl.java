package ru.yanmayak.t1openschool.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.exception.NotificationException;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Value("${spring.mail.email}") //enter your data in application.yaml to test email service
    private String emailAlias;

    private final JavaMailSender mailSender;
    @Override
    public void sendNotification(TaskDto taskDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("example@example.com"); //enter your email here to test email service
        message.setFrom(emailAlias);
        message.setSubject("Task status updated");
        message.setText("Task ID " + taskDto.getId() +
                " : status updated to " + taskDto.getStatus());
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new NotificationException("Failed to send notification", e);
        }
    }
}
