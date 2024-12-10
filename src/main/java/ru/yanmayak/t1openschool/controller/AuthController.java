package ru.yanmayak.t1openschool.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yanmayak.t1openschool.dto.auth.JwtAuthResponse;
import ru.yanmayak.t1openschool.dto.auth.SignInRequest;
import ru.yanmayak.t1openschool.dto.auth.SignUpRequest;
import ru.yanmayak.t1openschool.service.jwt.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/signup")
    public JwtAuthResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authService.signUp(signUpRequest);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/signin")
    public JwtAuthResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }
}
