package com.catalisa.gerenciadorDeTarefas.repository;

import com.catalisa.gerenciadorDeTarefas.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
 List<TaskModel> findByTitle(String title) ;
}
