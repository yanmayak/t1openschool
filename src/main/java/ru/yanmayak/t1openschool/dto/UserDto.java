package ru.yanmayak.t1openschool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Карточка пользователя")
public class UserDto {
    @Schema(name = "id пользователя")
    @NotBlank
    private UUID id;

    @Schema(name = "Имя пользователя")
    private String username;

    @Schema(name = "email jпльзователя")
    private String email;

}
