package ru.yanmayak.t1openschool.service.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.Task;
import ru.yanmayak.t1openschool.entity.TaskStatus;
import ru.yanmayak.t1openschool.entity.User;
import ru.yanmayak.t1openschool.exception.TaskNotFoundException;
import ru.yanmayak.t1openschool.kafka.KafkaTaskProducer;
import ru.yanmayak.t1openschool.mapper.TaskMapper;
import ru.yanmayak.t1openschool.mapper.TaskUpdate;
import ru.yanmayak.t1openschool.repository.TaskRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private TaskUpdate taskUpdate;
    @Mock
    private KafkaTaskProducer kafkaTaskProducer;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto taskDto;



    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("title");
        task.setDescription("description");
        task.setAuthor(new User());
        task.setStatus(TaskStatus.STATUS_WAITING);

        taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());

    }

    @Test
    @DisplayName("Создание задачи, возвращает TaskDto")
    public void createTask() {
        when(taskMapper.fromDto(taskDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDto);
        TaskDto result = taskService.createTask(taskDto);

        assertNotNull(result);

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("Получение задачи по ее UUID, возвращает TaskDto")
    public void getTaskById() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDto actualResult = taskService.getTask(task.getId());

        assertNotNull(actualResult);
        assertEquals(task.getId(), actualResult.getId());
        assertEquals(task.getTitle(), actualResult.getTitle());
        assertEquals(task.getDescription(), actualResult.getDescription());
        assertEquals(task.getStatus(), actualResult.getStatus());
    }

    @Test
    @DisplayName("Получение задачи по несуществующему UUID, прокидывается исключение")
    public void getTaskByIdAndThrowException() {
        UUID nonExistentId = UUID.randomUUID();
        when(taskRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        TaskNotFoundException exc = assertThrows(TaskNotFoundException.class,
                () -> taskService.getTask(nonExistentId));
        assertEquals("Task not found", exc.getMessage());
    }

    @Test
    @DisplayName("Обновление несуществующей задачи, прокидывается исключение")
    public void updateTaskShouldThrowWhenNotFound() {
        when(this.taskRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class,
                () -> this.taskService.updateTask(UUID.randomUUID(), new TaskDto())
        );
    }

    @Test
    @DisplayName("Обновление существующей задачи, исключение НЕ выкидывается")
    public void updateTaskShouldNotThrowWhenFound() {
        when(this.taskRepository.findById(any())).thenReturn(Optional.of(new Task()));
        assertDoesNotThrow(
                () -> this.taskService.updateTask(UUID.randomUUID(), new TaskDto())
        );
    }

    @Test
    @DisplayName("Проверка вызова метода deleteTask()")
    void deleteTask() {
        UUID taskToDeletingId = task.getId();
        when(taskRepository.existsById(taskToDeletingId)).thenReturn(true);
        taskService.deleteTask(taskToDeletingId);
        verify(taskRepository).deleteById(taskToDeletingId);
    }

    @Test
    @DisplayName("Удаление несуществующей задачи, выкидывается исключение")
    void deleteTaskShouldThrowWhenNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        assertThrows(TaskNotFoundException.class,
                () -> this.taskService.deleteTask(nonExistentId)
        );
    }

}
