package ru.yanmayak.t1openschool.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yanmayak.t1openschool.aspect.LogException;
import ru.yanmayak.t1openschool.aspect.LogExecution;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.service.TaskService;

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
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @LogExecution
    @LogException
    @PutMapping("/{id}")
    @Operation(summary = "Изменение задачи")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("id") UUID taskId, @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskDto));
    }

    @LogException
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление задачи")
    public void deleteTask(@PathVariable("id") UUID taskId) {
        taskService.deleteTask(taskId);
    }

    @LogExecution
    @LogException
    @GetMapping("/{id}")
    @Operation(summary = "Получение карточки задачи")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") UUID taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    @LogExecution
    @LogException
    @GetMapping
    @Operation(summary = "Получение списка задач")
    public ResponseEntity<List<TaskDto>> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }
}
