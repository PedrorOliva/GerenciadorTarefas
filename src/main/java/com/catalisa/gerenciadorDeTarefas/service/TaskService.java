package com.catalisa.gerenciadorDeTarefas.service;

import com.catalisa.gerenciadorDeTarefas.dto.TaskDTO;
import com.catalisa.gerenciadorDeTarefas.exceptions.HandleIDNotFound;
import com.catalisa.gerenciadorDeTarefas.exceptions.HandleNoHasTasks;
import com.catalisa.gerenciadorDeTarefas.exceptions.Validation;
import com.catalisa.gerenciadorDeTarefas.mapper.TaskMapper;
import com.catalisa.gerenciadorDeTarefas.model.TaskModel;
import com.catalisa.gerenciadorDeTarefas.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
  @Autowired
  TaskRepository taskRepository;
  @Autowired
  TaskMapper taskMapper;
  @Autowired
  Validation validation;

  public TaskDTO create(TaskDTO taskDTO) {
    TaskModel taskModel = new TaskModel();

    if (validation.isTitleValid(taskDTO.getTitle()) &&
        validation.isTittleCount(taskDTO.getTitle())) {
      taskModel.setTitle(taskDTO.getTitle());
    }
    if (validation.isDesciptionValid(taskDTO.getDescription()) &&
        validation.isDescriptionCount(taskDTO.getDescription())) {
      taskModel.setDescription(taskDTO.getDescription());
    }
    if (validation.isValidityValid(taskDTO.getValidity()) &&
        validation.isValidityAfter(taskDTO.getValidity())) {
      taskModel.setValidity(taskDTO.getValidity());
    }
    return taskMapper.toTaskDTO(taskRepository.save(taskModel));
  }

  public List<TaskDTO> findAll() {
    List<TaskModel> tasks = taskRepository.findAll();

    if (tasks.isEmpty()) {
      throw new HandleNoHasTasks("Nenhuma tarefa encontrada!");
    }

    List<TaskDTO> taskDTOS = new ArrayList<>();

    for (TaskModel taskModel : tasks) {
      taskDTOS.add(taskMapper.toTaskDTO(taskModel));
    }
    return taskDTOS;
  }

  public Optional<TaskDTO> findTaskById(Long id) {

    Optional<TaskModel> taskModel = taskRepository.findById(id);

    if (taskModel.isEmpty()) {
      throw new HandleIDNotFound("Id não encontrado!");
    }

    TaskModel task = taskRepository.findById(id).get();
    TaskDTO taskDTO = taskMapper.toTaskDTO(task);
    return Optional.of(taskDTO);
  }

  public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
    Optional<TaskModel> taskModelOptional = taskRepository.findById(id);

    if (taskModelOptional.isEmpty()) {
      throw new HandleIDNotFound("Id não encontrado!");
    }

    TaskModel task = taskModelOptional.get();
    if (taskDTO.getTitle() != null) {
      task.setTitle(taskDTO.getTitle());
    }
    if (taskDTO.getDescription() != null) {
      task.setDescription(taskDTO.getDescription());
    }
    if (taskDTO.getValidity() != null) {
      task.setValidity(taskDTO.getValidity());
    }
    if (taskDTO.getStatus() != null) {
      task.setStatus(taskDTO.getStatus());
    }
    taskRepository.save(task);
    return taskMapper.toTaskDTO(task);
  }

  public void deleteTask(Long id) {
    Optional<TaskModel> taskModel = taskRepository.findById(id);

    if (taskModel.isEmpty()) {
      throw new HandleIDNotFound("Id não encontrado!");
    }
    taskRepository.deleteById(id);
  }

}
