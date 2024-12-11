package ru.yanmayak.t1openschool.service.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.yanmayak.t1openschool.dto.auth.JwtAuthResponse;
import ru.yanmayak.t1openschool.dto.auth.SignInRequest;
import ru.yanmayak.t1openschool.dto.auth.SignUpRequest;
import ru.yanmayak.t1openschool.entity.User;
import ru.yanmayak.t1openschool.entity.UserRole;
import ru.yanmayak.t1openschool.exception.UserNotFoundException;
import ru.yanmayak.t1openschool.repository.UserRepository;
import ru.yanmayak.t1openschool.service.user.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private SignInRequest signInRequest;
    private SignUpRequest signUpRequest;
    private User user;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest("testuser", "test@example.com", "password");
        signInRequest = new SignInRequest("testuser", "test@example.com");
        user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .role(UserRole.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("Регистрация пользователя")
    public void signUp() {
        when(userService.create(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("token");

        JwtAuthResponse response = authService.signUp(signUpRequest);

        assertNotNull(response);
        assertEquals("token", response.getToken());
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void signIn() {
        when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("token");
        UserDetailsService detailsService = mock(UserDetailsService.class);
        when(userService.userDetailsService()).thenReturn(detailsService);
        when(detailsService.loadUserByUsername("testuser")).thenReturn(user);

        JwtAuthResponse jwtAuthResponse = authService.signIn(signInRequest);

        assertNotNull(jwtAuthResponse);
        assertEquals( "token", jwtAuthResponse.getToken());
        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(any());
    }

    @Test
    @DisplayName("При авторизации пользователь не найден")
    public void signIn_UserNotFound() {
        lenient().when(userRepository.findByEmail(signInRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.signIn(signInRequest));
        verify(authenticationManager, never()).authenticate(any());

    }
}
