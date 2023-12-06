package com.catalisa.gerenciadorDeTarefas.mapper;

import com.catalisa.gerenciadorDeTarefas.dto.TaskDTO;
import com.catalisa.gerenciadorDeTarefas.model.TaskModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {
  @InjectMocks
  private TaskMapper taskMapper;

  @DisplayName("Should convert TaskModel to TaskDTO")
  @Test
  public void testTaskToDTO() {
    LocalDate validity = LocalDate.now().plusDays(1);
    TaskModel task = new TaskModel();
    task.setId(1L);
    task.setTitle("teste");
    task.setDescription("descrição");
    task.setValidity(validity);
    task.setStatus("aberta");

    TaskDTO taskDTO = taskMapper.toTaskDTO(task);

    assertEquals("teste", taskDTO.getTitle());
    assertEquals("descrição", taskDTO.getDescription());
    assertEquals(validity, taskDTO.getValidity());
    assertEquals("aberta", taskDTO.getStatus());
  }
}
