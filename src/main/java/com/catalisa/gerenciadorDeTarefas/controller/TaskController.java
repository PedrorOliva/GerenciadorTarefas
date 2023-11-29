package com.catalisa.gerenciadorDeTarefas.controller;

import com.catalisa.gerenciadorDeTarefas.dto.TaskDTO;
import com.catalisa.gerenciadorDeTarefas.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/task")
public class TaskController {
  @Autowired
  TaskService taskService;

  @PostMapping
  public ResponseEntity<TaskDTO> registerTask(@Valid @RequestBody TaskDTO taskDTO) {
    TaskDTO newTask = taskService.create(taskDTO);
    return new ResponseEntity<>(newTask, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<TaskDTO>> listAllTasks() {
    return ResponseEntity.ok(taskService.findAll());
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<?> taskById(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.findTaskById(id));
  }

  @GetMapping(path = "/searchTitle")
  public ResponseEntity<List<TaskDTO>> findByTitle(@RequestParam String title) {
    return ResponseEntity.ok(taskService.findTaskByTitle(title));
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
    return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();
  }
}
