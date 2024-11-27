package ru.yanmayak.t1openschool.service.task;


import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.TaskStatus;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskDto createTask(TaskDto task);
    TaskDto getTask(UUID taskId);
    TaskDto updateTask(UUID taskId, TaskDto task);
    TaskDto updateTaskStatus(UUID taskId, TaskStatus taskStatus);
    List<TaskDto> getTasks();
    void deleteTask(UUID taskId);
}
