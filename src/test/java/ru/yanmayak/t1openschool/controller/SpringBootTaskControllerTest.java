package ru.yanmayak.t1openschool.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yanmayak.t1openschool.AbstractContainerBaseTest;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.Task;
import ru.yanmayak.t1openschool.entity.TaskStatus;
import ru.yanmayak.t1openschool.entity.User;
import ru.yanmayak.t1openschool.mapper.TaskMapper;
import ru.yanmayak.t1openschool.repository.TaskRepository;
import ru.yanmayak.t1openschool.service.task.TaskService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SpringBootTaskControllerTest extends AbstractContainerBaseTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Autowired
//    private TaskMapper taskMapper;
//
//    @BeforeEach
//    void setUp() {
//        taskRepository.deleteAll();
//        saveTestTasks();
//    }
//
//    private void saveTestTasks() {
//        User author = new User();
//        author.setId(UUID.randomUUID());
//        author.setUsername("test");
//        author.setPassword("test");
//        author.setEmail("test@yandex.ru");
//
//        Task task1 = new Task();
//        task1.setTitle("title1");
//        task1.setDescription("description1");
//        task1.setStatus(TaskStatus.STATUS_WAITING);
//        task1.setAuthor(author);
//
//        Task task2 = new Task();
//        task2.setTitle("title2");
//        task2.setDescription("description2");
//        task2.setStatus(TaskStatus.STATUS_RUNNING);
//        task2.setAuthor(author);
//
//        Task task3 = new Task();
//        task3.setTitle("title3");
//        task3.setDescription("description3");
//        task3.setStatus(TaskStatus.STATUS_FINISHED);
//        task3.setAuthor(author);
//    }
//
//    @Test
//    @DisplayName("")
//    void getAllTasks() throws Exception {
//
//    }
//
//    @Test
//    @DisplayName("")
//    void getTaskById() throws Exception {
//        UUID taskId = UUID.randomUUID();
//        TaskDto taskDto = new TaskDto();
//        taskDto.setId(taskId);
//        taskDto.setTitle("test title");
//        taskDto.setDescription("test description");
//
//
//
//        mockMvc.perform(get("/api/v1/tasks/{id}", taskId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("test title"))
//                .andExpect(jsonPath("$.description").value("test description"));
//
//
//    }


}
