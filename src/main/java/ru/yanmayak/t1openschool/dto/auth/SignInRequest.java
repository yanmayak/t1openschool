package ru.yanmayak.t1openschool.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Запрост на аутентификацию")
public class SignInRequest {
    @Schema(description = "E-mail", example = "example@example.ru")
    @NotBlank
    @Email
    private String email;

    @Schema(description = "Пароль", example = "passWord111")
    @Size(min = 5, max = 20, message = "От 5 до 20 символов")
    @NotBlank
    private String password;
}
