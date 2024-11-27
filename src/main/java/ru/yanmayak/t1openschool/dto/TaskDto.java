package ru.yanmayak.t1openschool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yanmayak.t1openschool.entity.TaskStatus;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Карточка задачи")
public class TaskDto {
    @Schema(description = "Идентификатор задачи")
    @NotBlank
    private UUID id;

    @Schema(description = "Заголовок задачи")
    @NotBlank
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Идентификатор автора задачи")
    @NotBlank
    private UUID userId;

    @Schema(description = "Статус задачи")
    @NotBlank
    private TaskStatus status;
}
