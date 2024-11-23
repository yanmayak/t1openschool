package ru.yanmayak.t1openschool.service;


import ru.yanmayak.t1openschool.dto.TaskDto;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskDto createTask(TaskDto task);
    TaskDto getTask(UUID taskId);
    TaskDto updateTask(UUID taskId, TaskDto task);
    List<TaskDto> getTasks();
    void deleteTask(UUID taskId);
}
