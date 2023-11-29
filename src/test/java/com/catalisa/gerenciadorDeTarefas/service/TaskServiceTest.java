package com.catalisa.gerenciadorDeTarefas.service;

import com.catalisa.gerenciadorDeTarefas.dto.TaskDTO;
import com.catalisa.gerenciadorDeTarefas.exceptions.*;
import com.catalisa.gerenciadorDeTarefas.mapper.TaskMapper;
import com.catalisa.gerenciadorDeTarefas.model.TaskModel;
import com.catalisa.gerenciadorDeTarefas.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
  @Mock
  private TaskRepository taskRepository;
  @Mock
  private TaskMapper taskMapper;
  @Mock
  private Validation validation;
  @InjectMocks
  private TaskService taskService;

  @BeforeEach
  void config() {
    MockitoAnnotations.openMocks(this);
  }

  @DisplayName("Should create a new task")
  @Test
  public void testCreateTask() throws Exception {
    TaskDTO task = new TaskDTO();
    task.setTitle("titulo");
    task.setDescription("descrição");
    task.setValidity(LocalDate.now().plusDays(1));
    task.setStatus("aberta");

    when(validation.isTitleValid(anyString())).thenReturn(true);
    when(validation.isTittleCount(anyString())).thenReturn(true);
    when(validation.isDesciptionValid(anyString())).thenReturn(true);
    when(validation.isDescriptionCount(anyString())).thenReturn(true);
    when(validation.isValidityValid(any(LocalDate.class))).thenReturn(true);
    when(validation.isValidityAfter(any(LocalDate.class))).thenReturn(true);

    taskService.create(task);

    assertNotNull(task);
  }

  @DisplayName("Should return a list of tasks")
  @Test
  public void testListAllTaks() throws Exception {
    TaskModel task1 = new TaskModel();
    TaskModel task2 = new TaskModel();
    List<TaskModel> taskModelList = Arrays.asList(task1, task2);

    when(taskRepository.findAll()).thenReturn(taskModelList);

    List<TaskDTO> taskDTOList = taskService.findAll();

    assertEquals(2, taskDTOList.size());
  }

  @DisplayName("Shoud return an exception if there is no task")
  @Test
  public void testNoHasTask() throws Exception {
    when(taskRepository.findAll()).thenReturn(new ArrayList<>());

    assertThrows(HandleNoHasTasks.class, () -> {
      taskService.findAll();
    });

  }

  @DisplayName("Should return a task by ID")
  @Test
  public void testFindTaskById() throws Exception {
    TaskModel taskModel = new TaskModel();
    taskModel.setId(1L);

    TaskDTO taskDTO = new TaskDTO();

    when(taskRepository.findById(1L)).thenReturn(Optional.of(taskModel));
    when(taskMapper.toTaskDTO(taskModel)).thenReturn(taskDTO);

    Optional<TaskDTO> result = taskService.findTaskById(1L);

    assertTrue(result.isPresent());
    assertEquals(taskDTO, result.get());
  }

  @DisplayName("Should return exception when id not found")
  @Test
  public void testIdNotFound() throws Exception {
    Long id = 1L;

    when(taskRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(HandleIDNotFound.class, () -> {
      taskService.findTaskById(id);
    });
  }

  @DisplayName("Should return a task by title")
  @Test
  public void testFindTaskByTitle() throws Exception {
    String name = "titulo";

    TaskModel taskModel = new TaskModel();
    TaskDTO taskDTO = new TaskDTO();

    List<TaskModel> taskModelList = Arrays.asList(taskModel);
    List<TaskDTO> taskDTOList = Arrays.asList(taskDTO);

    when(taskRepository.findByTitle(Mockito.anyString())).thenReturn(taskModelList);
    when(taskMapper.toTaskDTO(taskModel)).thenReturn(taskDTO);

    List<TaskDTO> result = taskService.findTaskByTitle(name);

    assertEquals(taskDTOList, result);
  }

  @DisplayName("Should return a exception when title not found")
  @Test
  public void testTitleNotFound() throws Exception {


    when(taskRepository.findByTitle(anyString())).thenReturn(new ArrayList<>());

    assertThrows(HandleTitleNotFound.class, () -> {
      taskService.findTaskByTitle("title");
    });
  }

  @DisplayName("Should update a task by ID")
  @Test
  public void testUpdateTaskById() throws Exception {
    Long idTask = 1L;
    TaskDTO updateTaskDTO = new TaskDTO();
    TaskModel taskModel = new TaskModel();

    Optional<TaskModel> taskModelOptional = Optional.of(taskModel);

    when(taskRepository.findById(idTask)).thenReturn(taskModelOptional);
    when(taskMapper.toTaskDTO(taskModel)).thenReturn(updateTaskDTO);

    TaskDTO result = taskService.updateTask(idTask, updateTaskDTO);

    verify(taskRepository).save(taskModel);

    assertEquals(updateTaskDTO, result);
  }

  @DisplayName("Should delete a task by ID")
  @Test
  public void testDeleteTaskById() throws Exception {
    TaskModel taskExist = new TaskModel();
    taskExist.setId(1L);

    Optional<TaskModel> taskModelExist = Optional.of(taskExist);

    when(taskRepository.findById(1L)).thenReturn(taskModelExist);

    taskService.deleteTask(taskExist.getId());
  }
}

