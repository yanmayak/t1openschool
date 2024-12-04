package ru.yanmayak.t1openschool.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.Task;
import ru.yanmayak.t1openschool.exception.NotificationException;
import ru.yanmayak.t1openschool.service.notification.NotificationService;
import ru.yanmayak.t1openschool.service.task.TaskService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTaskConsumer {
    private final NotificationService notificationService;

    @KafkaListener(id = "t1openschool",
            topics = "task_status",
            containerFactory = "kafkaListenerContainerFactory"
            )
    public void listener(@Payload List<TaskDto> messageList,
                         Acknowledgment ack) {
        log.debug("Task consumer: обработка изменений статуса задач");
        try {
            messageList.forEach(notificationService::sendNotification);
        } catch (NotificationException exc) {
            log.error("Произошла ошибка {} во время отправки уведомлений", exc.getCause().getMessage());

        } finally {
            ack.acknowledge();
        }
        log.debug("Client  consumer: записи обработаны");
    }
}
