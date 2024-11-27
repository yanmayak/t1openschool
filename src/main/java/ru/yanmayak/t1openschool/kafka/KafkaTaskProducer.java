package ru.yanmayak.t1openschool.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.TaskStatus;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTaskProducer {
    private final KafkaTemplate<String, TaskDto> kafkaTemplate;

    public void sendStatusUpdate(UUID taskId, TaskStatus taskStatus) {
        kafkaTemplate.send("task_status", new TaskDto(taskId, null, null, null, taskStatus));
    }
}
