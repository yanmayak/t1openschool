package ru.yanmayak.t1openschool.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yanmayak.aspect.LogException;
import ru.yanmayak.aspect.LogExecution;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.TaskStatus;
import ru.yanmayak.t1openschool.service.task.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @LogExecution
    @LogException
    @PostMapping
    @Operation(summary = "Создание задачи")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@Valid @RequestBody TaskDto task) {
        return taskService.createTask(task);
    }

    @LogExecution
    @LogException
    @PutMapping("/{id}")
    @Operation(summary = "Изменение задачи")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@PathVariable("id") UUID taskId, @Valid @RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskId, taskDto);
    }

    @LogException
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление задачи")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("id") UUID taskId) {
        taskService.deleteTask(taskId);
    }

    @LogExecution
    @LogException
    @GetMapping("/{id}")
    @Operation(summary = "Получение карточки задачи")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTask(@PathVariable("id") UUID taskId) {
        return taskService.getTask(taskId);
    }

    @LogExecution
    @LogException
    @GetMapping
    @Operation(summary = "Получение списка задач")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getTasks() {
        return taskService.getTasks();
    }

    @LogException
    @LogExecution
    @PatchMapping("/{id}")
    @Operation(summary = "Изменение статуса задачи")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@PathVariable("id") UUID taskId, @Valid @RequestParam TaskStatus status) {
        return taskService.updateTaskStatus(taskId, status);
    }
}
