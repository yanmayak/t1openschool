package ru.yanmayak.t1openschool.service.task;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.Task;
import ru.yanmayak.t1openschool.entity.TaskStatus;
import ru.yanmayak.t1openschool.exception.TaskNotFoundException;
import ru.yanmayak.t1openschool.exception.UnauthorizedException;
import ru.yanmayak.t1openschool.exception.UserNotFoundException;
import ru.yanmayak.t1openschool.repository.TaskRepository;
import ru.yanmayak.t1openschool.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service("TaskSecure")
public class TaskSecureService implements TaskService {
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskSecureService(
            @Qualifier("Tasks") TaskService taskService,
            UserRepository userRepository,
            TaskRepository taskRepository
    ) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDto createTask(TaskDto task) {
        if(!taskCreatedByUser(SecurityContextHolder.getContext().getAuthentication().getName(), task.getAuthor().getId())) {
            throw new UnauthorizedException("User not allowed to create a new task");
        }
        return taskService.createTask(task);
    }

    @Override
    public TaskDto getTask(UUID taskId) {
        return taskService.getTask(taskId);
    }

    @Override
    public TaskDto updateTask(UUID taskId, TaskDto task) {
        boolean isTaskAuthor = taskCreatedByUser(
                SecurityContextHolder.getContext().getAuthentication().getName(),
                taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task Not Found")).getAuthor().getId()
        );
        if(!isTaskAuthor) {
            throw new UnauthorizedException("User not allowed to update other user's task");
        }
        return taskService.updateTask(taskId, task);
    }

    @Override
    public TaskDto updateTaskStatus(UUID taskId, TaskStatus taskStatus) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task Not Found"));
        boolean isTaskAuthor = taskCreatedByUser(username, task.getAuthor().getId());
        if(!isTaskAuthor) {
            throw new UnauthorizedException("Only task author and task assignee can change task status");
        }
        return taskService.updateTaskStatus(taskId, taskStatus);
    }

    public List<TaskDto> getTasks() {
        return taskService.getTasks();
    }

    @Override
    public void deleteTask(UUID taskId) {
        boolean isTaskAuthor = taskCreatedByUser(
                SecurityContextHolder.getContext().getAuthentication().getName(),
                taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task Not Found")).getAuthor().getId()
        );
        if(!isTaskAuthor) {
            throw new UnauthorizedException("User not allowed to delete other user's task");
        }
        taskService.deleteTask(taskId);

    }

    private boolean taskCreatedByUser(String username, UUID userId) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Author not found"))
                .getId()
                .equals(userId);
    }
}
