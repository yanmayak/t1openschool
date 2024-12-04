package ru.yanmayak.t1openschool.service.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.Task;
import ru.yanmayak.t1openschool.entity.TaskStatus;
import ru.yanmayak.t1openschool.exception.TaskNotFoundException;
import ru.yanmayak.t1openschool.kafka.KafkaTaskProducer;
import ru.yanmayak.t1openschool.mapper.TaskMapper;
import ru.yanmayak.t1openschool.mapper.TaskUpdate;
import ru.yanmayak.t1openschool.repository.TaskRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskUpdate taskUpdate;
    private final KafkaTaskProducer kafkaTaskProducer;

    @Override
    public TaskDto createTask(TaskDto task) {
        return taskMapper.toDto(
                taskRepository.save(taskMapper.fromDto(task)));
    }

    @Override
    public TaskDto getTask(UUID taskId) {
        return taskMapper.toDto(
                taskRepository.findById(taskId)
                        .orElseThrow(() -> new TaskNotFoundException("Task not found"))
        );
    }

    @Override
    public TaskDto updateTask(UUID taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        taskUpdate.updateTask(task, taskDto);
        return taskMapper.toDto(
                taskRepository.save(task)
        );
    }

    @Override
    public TaskDto updateTaskStatus(UUID taskId, TaskStatus taskStatus) {
        return taskMapper.toDto(
                taskRepository.findById(taskId)
                        .map(task -> {
                            task.setStatus(taskStatus);
                            Task savedTask = taskRepository.save(task);
                            kafkaTaskProducer.sendStatusUpdate(taskId, taskStatus);
                            return savedTask;
                        }).orElseThrow(() -> new TaskNotFoundException("Task not found"))
        );
    }

    @Override
    public List<TaskDto> getTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(UUID taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("Задача не найдена");
        }
        taskRepository.deleteById(taskId);
    }
}
