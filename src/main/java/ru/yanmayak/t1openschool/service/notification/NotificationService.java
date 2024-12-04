package ru.yanmayak.t1openschool.service.notification;

import ru.yanmayak.t1openschool.dto.TaskDto;

public interface NotificationService {
    void sendNotification(TaskDto taskDto);
}
