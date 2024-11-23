package ru.yanmayak.t1openschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.Task;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class TaskMapper {
    public abstract TaskDto toDto(Task task);
    public abstract Task fromDto(TaskDto dto);
}
