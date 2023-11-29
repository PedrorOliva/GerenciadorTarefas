package com.catalisa.gerenciadorDeTarefas.controller;

import com.catalisa.gerenciadorDeTarefas.dto.TaskDTO;
import com.catalisa.gerenciadorDeTarefas.exceptions.HandleIDNotFound;
import com.catalisa.gerenciadorDeTarefas.exceptions.HandleNoHasTasks;
import com.catalisa.gerenciadorDeTarefas.exceptions.HandleTitleNotFound;
import com.catalisa.gerenciadorDeTarefas.mapper.TaskMapper;
import com.catalisa.gerenciadorDeTarefas.repository.TaskRepository;
import com.catalisa.gerenciadorDeTarefas.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private TaskMapper taskMapper;
  @MockBean
  private TaskRepository taskRepository;
  @MockBean
  private TaskService taskService;

  @DisplayName("Should register a task")
  @Test
  public void testRegisterTastk() throws Exception {
    String validityDate = LocalDate.now().plusDays(1).toString();
    TaskDTO task = new TaskDTO();
    task.setTitle("titulo");
    task.setDescription("descrição");
    task.setValidity(LocalDate.now().plusDays(1));
    task.setStatus("aberta");

    Mockito.when(taskService.create(any())).thenReturn(task);

    mockMvc.perform(post("/api/task")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(task)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value("titulo"))
        .andExpect(jsonPath("$.description").value("descrição"))
        .andExpect(jsonPath("$.validity").value(validityDate))
        .andExpect(jsonPath("$.status").value("aberta"));
  }

  @DisplayName("Should display all tasks")
  @Test
  public void testListAllTasks() throws Exception {
    String validityDate = LocalDate.now().plusDays(1).toString();
    List<TaskDTO> taskDTOList = new ArrayList<>();
    TaskDTO task1 = new TaskDTO();
    task1.setTitle("tarefa1");
    task1.setDescription("descrição");
    task1.setValidity(LocalDate.now().plusDays(1));
    task1.setStatus("aberta");

    TaskDTO task2 = new TaskDTO();
    task2.setTitle("tarefa2");
    task2.setDescription("segunda descrição");
    task2.setValidity(LocalDate.now().plusDays(1));
    task2.setStatus("aberta");

    taskDTOList.add(task1);
    taskDTOList.add(task2);

    Mockito.when(taskService.findAll()).thenReturn(taskDTOList);

    mockMvc.perform(get("/api/task"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].title").value("tarefa1"))
        .andExpect(jsonPath("$[0].description").value("descrição"))
        .andExpect(jsonPath("$[0].validity").value(validityDate))
        .andExpect(jsonPath("$[0].status").value("aberta"))
        .andExpect(jsonPath("$[1].title").value("tarefa2"))
        .andExpect(jsonPath("$[1].description").value("segunda descrição"))
        .andExpect(jsonPath("$[1].validity").value(validityDate))
        .andExpect(jsonPath("$[1].status").value("aberta"));
  }

  @DisplayName("Should return an exception if there is no task")
  @Test
  public void testNoHasTask() throws Exception {

    doThrow(HandleNoHasTasks.class).when(taskService).findAll();

    mockMvc.perform(get("/api/task"))
        .andExpect(status().isNotFound());
  }

  @DisplayName("Should find a task by iD")
  @Test
  public void testFindTaskById() throws Exception {
    String validityDate = LocalDate.now().plusDays(1).toString();
    TaskDTO task = new TaskDTO();
    task.setTitle("tarefa");
    task.setDescription("descrição");
    task.setValidity(LocalDate.now().plusDays(1));
    task.setStatus("aberta");

    Mockito.when(taskService.findTaskById(1L)).thenReturn(Optional.of(task));

    mockMvc.perform(get("/api/task/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.title").value("tarefa"))
        .andExpect(jsonPath("$.description").value("descrição"))
        .andExpect(jsonPath("$.validity").value(validityDate))
        .andExpect(jsonPath("$.status").value("aberta"));
  }

  @DisplayName("Sould return a exception if theres is no ID")
  @Test
  public void testIdNotFound() throws Exception {
    Long id = 1L;
    doThrow(HandleIDNotFound.class).when(taskService).findTaskById(id);

    mockMvc.perform(get("/api/task/{id}", 1L))
        .andExpect(status().isNotFound());
  }

  @DisplayName("Sould find a task by title")
  @Test
  public void testFindByTitle() throws Exception {
    String validityDate = LocalDate.now().plusDays(1).toString();
    List<TaskDTO> taskDTOList = new ArrayList<>();
    TaskDTO task = new TaskDTO();
    task.setTitle("tarefa");
    task.setDescription("descrição");
    task.setValidity(LocalDate.now().plusDays(1));
    task.setStatus("aberta");

    TaskDTO task2 = new TaskDTO();
    task2.setTitle("tarefa2");

    taskDTOList.add(task);
    taskDTOList.add(task2);

    Mockito.when(taskService.findTaskByTitle("tarefa")).thenReturn(taskDTOList);

    String paramName = "title";
    String paramValue = "tarefa";

    mockMvc.perform(get("/api/task/searchTitle")
            .param(paramName, paramValue))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].title").value("tarefa"))
        .andExpect(jsonPath("$[0].description").value("descrição"))
        .andExpect(jsonPath("$[0].validity").value(validityDate))
        .andExpect(jsonPath("$[0].status").value("aberta"));
  }

  @DisplayName("Should return a exception if there is no title")
  @Test
  public void testHasNoTitle() throws Exception {
    String paramName = "title";
    String paramValue = "titulo";

    doThrow(HandleTitleNotFound.class).when(taskService).findTaskByTitle(paramValue);

    mockMvc.perform(get("/api/task/searchTitle")
            .param(paramName, paramValue))
        .andExpect(status().isNotFound());
  }

  @DisplayName("Should update a task")
  @Test
  public void testUpdateTask() throws Exception {
    String validityDate = LocalDate.now().plusDays(1).toString();
    TaskDTO taskDTO = new TaskDTO("Titulo", "descrição", LocalDate.now().plusDays(1), "aberta");

    Mockito.when(taskService.updateTask(Mockito.any(), Mockito.any(taskDTO.getClass()))).thenReturn(taskDTO);

    mockMvc.perform(put("/api/task/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(taskDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Titulo"))
        .andExpect(jsonPath("$.description").value("descrição"))
        .andExpect(jsonPath("$.validity").value(validityDate))
        .andExpect(jsonPath("$.status").value("aberta"));

  }

  @DisplayName("Should return a exception there is no ID to update")
  @Test
  public void testUpdateIdNotFound() throws Exception {
    TaskDTO task = new TaskDTO();
    doThrow(HandleIDNotFound.class).when(taskService).updateTask(1L, task);

    mockMvc.perform(put("/api/task/{id}", 1L))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("Should delete a task by id")
  @Test
  public void testDeleteTaskByID() throws Exception {
    when(taskService.findTaskById(1L)).thenReturn(Optional.of(new TaskDTO()));

    mockMvc.perform(delete("/api/task/{id}", 1L))
        .andExpect(status().isNoContent());
  }

  @DisplayName("Sould return a exception there is no ID to delete")
  @Test
  public void testDeleteByIdNotFound() throws Exception {
    doThrow(HandleIDNotFound.class).when(taskService).deleteTask(1L);

    mockMvc.perform(delete("/api/task/{id}", 1L))
        .andExpect(status().isNotFound());
  }
}
