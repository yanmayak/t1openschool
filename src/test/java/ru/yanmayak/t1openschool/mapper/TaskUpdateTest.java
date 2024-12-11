package ru.yanmayak.t1openschool.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.Task;
import ru.yanmayak.t1openschool.entity.TaskStatus;
import ru.yanmayak.t1openschool.entity.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskUpdateTest {
    @InjectMocks
    private TaskUpdateImpl taskUpdate;
    @Mock
    private TaskMapper taskMapper;

    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(UUID.randomUUID());
        task.setTitle("title");
        task.setDescription("description");
        task.setAuthor(new User());
        task.setStatus(TaskStatus.STATUS_WAITING);

        taskDto = new TaskDto();
        taskDto.setTitle("new title");
        taskDto.setDescription("new description");
    }

    @Test
    @DisplayName("Внесение изменений в задачу")
    public void updateTask() {
        taskUpdate.updateTask(task, taskDto);

        assertEquals("new title", task.getTitle());
        assertEquals("new description", task.getDescription());

    }

    @Test
    @DisplayName("Маппинг обновлени задач без изменений их параметров")
    public void updateTaskWithNoChanges() {
        taskDto.setTitle("title");
        taskDto.setDescription("description");

        taskUpdate.updateTask(task, taskDto);

        assertEquals("title", task.getTitle());
        assertEquals("description", task.getDescription());
    }

}
