package com.catalisa.gerenciadorDeTarefas.mapper;

import com.catalisa.gerenciadorDeTarefas.dto.TaskDTO;
import com.catalisa.gerenciadorDeTarefas.model.TaskModel;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
  public TaskDTO toTaskDTO(TaskModel taskModel) {
    TaskDTO taskDTO = new TaskDTO();

    taskDTO.setTitle(taskModel.getTitle());
    taskDTO.setDescription(taskModel.getDescription());
    taskDTO.setValidity(taskModel.getValidity());
    taskDTO.setStatus(taskModel.getStatus());

    return taskDTO;
  }
}
