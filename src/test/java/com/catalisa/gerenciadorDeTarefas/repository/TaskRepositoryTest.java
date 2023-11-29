package com.catalisa.gerenciadorDeTarefas.repository;

import com.catalisa.gerenciadorDeTarefas.dto.TaskDTO;
import com.catalisa.gerenciadorDeTarefas.model.TaskModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class TaskRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private TaskRepository taskRepository;

  @DisplayName("Should find a task by title")
  @Test
  public void findTaskByTitle() {
    TaskModel task = new TaskModel();
    task.setTitle("titulo");
    task.setDescription("descrição");
    task.setValidity(LocalDate.now().plusDays(1));
    task.setStatus("aberta");


    entityManager.persistAndFlush(task);

    List<TaskModel> taskModelList = taskRepository.findByTitle("titulo");

    assertThat(taskRepository.findByTitle("titulo")).contains(task);
  }
}
