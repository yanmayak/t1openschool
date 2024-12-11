package ru.yanmayak.t1openschool.mapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.dto.UserDto;
import ru.yanmayak.t1openschool.entity.Task;
import ru.yanmayak.t1openschool.entity.TaskStatus;
import ru.yanmayak.t1openschool.entity.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {
    private TaskMapper taskMapper;

    @BeforeEach
    public void setUp() {
        taskMapper = Mappers.getMapper(TaskMapper.class);
    }

    @Test
    @DisplayName("Маппинг dto в сущность")
    public void mapTaskToTaskDto() {
        Task task = new Task();

        task.setId(UUID.randomUUID());
        task.setTitle("title");
        task.setDescription("description");
        task.setAuthor(new User());
        task.setStatus(TaskStatus.STATUS_WAITING);

        TaskDto taskDto = taskMapper.toDto(task);

        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getAuthor().getId(), taskDto.getAuthor().getId());
    }

    @Test
    @DisplayName("Маппинг сущности в dto")
    public void mapTaskDtoToTask() {
        TaskDto taskDto = new TaskDto();

        taskDto.setId(UUID.randomUUID());
        taskDto.setTitle("title");
        taskDto.setDescription("description");
        taskDto.setStatus(TaskStatus.STATUS_WAITING);
        taskDto.setAuthor(new UserDto());

        Task task = taskMapper.fromDto(taskDto);

        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getDescription(), taskDto.getDescription());
        assertEquals(task.getStatus(), taskDto.getStatus());
        assertEquals(task.getAuthor().getId(), taskDto.getAuthor().getId());
    }
}
