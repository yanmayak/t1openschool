package ru.yanmayak.t1openschool.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.yanmayak.t1openschool.entity.User;
import ru.yanmayak.t1openschool.exception.UserNotFoundException;
import ru.yanmayak.t1openschool.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@email.com");
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createUser() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals(user.getUsername(), createdUser.getUsername());
        assertEquals(user.getEmail(), createdUser.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Создание нового пользователя с уже зарегистрированным email")
    public void createUserWhenEmailExists() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () ->
                userService.create(user));

        assertEquals("Email already exists", exc.getMessage());
    }

    @Test
    @DisplayName("Создание нового пользователя с уже зарегистрированным никнеймом")
    public void createUserWhenUsernameExists() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () ->
                userService.create(user));

        assertEquals("Username already exists", exc.getMessage());
    }

    @Test
    @DisplayName("Получение существующего пользователя")
    public void getUserByUsernameWhenUsernameExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User foundUser = userService.getByUsername(user.getUsername());

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    @DisplayName("Получение несуществующего пользователя, прокидывается исключение")
    public void getUserByUsernameWhenUsernameDoesNotExist() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        UserNotFoundException exc = assertThrows(UserNotFoundException.class, () ->
                userService.getByUsername(user.getUsername()));

        assertEquals("Username not found", exc.getMessage());
    }

    @Test
    @DisplayName("Получение пользователя")
    public void getCurrentUser() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getUsername());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User currentUser = userService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals(user.getUsername(), currentUser.getUsername());
    }

}
