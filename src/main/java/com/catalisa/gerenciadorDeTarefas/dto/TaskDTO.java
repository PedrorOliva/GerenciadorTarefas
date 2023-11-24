package com.catalisa.gerenciadorDeTarefas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskDTO {
  private String title;
  private String description;
  private LocalDate validity;
  private String status;

}
