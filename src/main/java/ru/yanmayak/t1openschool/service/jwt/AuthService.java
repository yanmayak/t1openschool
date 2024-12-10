package ru.yanmayak.t1openschool.service.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yanmayak.t1openschool.dto.auth.JwtAuthResponse;
import ru.yanmayak.t1openschool.dto.auth.SignInRequest;
import ru.yanmayak.t1openschool.dto.auth.SignUpRequest;
import ru.yanmayak.t1openschool.entity.UserRole;
import ru.yanmayak.t1openschool.exception.UserNotFoundException;
import ru.yanmayak.t1openschool.repository.UserRepository;
import ru.yanmayak.t1openschool.service.user.UserService;
import ru.yanmayak.t1openschool.entity.User;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtAuthResponse signUp(SignUpRequest request) {
        return new JwtAuthResponse(
                jwtService.generateToken(
                        userService.create(
                                User.builder()
                                        .username(request.getUsername())
                                        .email(request.getEmail())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .role(UserRole.ROLE_USER)
                                        .build()
                        )
                )
        );
    }

    @Transactional
    public JwtAuthResponse signIn(SignInRequest signInRequest) {
        return userRepository.findByEmail(signInRequest.getEmail())
                .map(user ->
                        authorize(
                                user.getUsername(),
                                signInRequest.getPassword()
                        )
                )
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private JwtAuthResponse authorize(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return new JwtAuthResponse(
                jwtService.generateToken(
                        userService.userDetailsService()
                                .loadUserByUsername(username)
                )
        );
    }
}
