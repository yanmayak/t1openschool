package ru.yanmayak.t1openschool.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yanmayak.t1openschool.dto.TaskDto;
import ru.yanmayak.t1openschool.entity.Task;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public abstract class TaskUpdate {
    @Autowired
    private TaskMapper taskMapper;

    public abstract void updateTask(@MappingTarget Task task, TaskDto taskDto);
}
