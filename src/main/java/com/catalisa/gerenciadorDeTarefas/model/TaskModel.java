package com.catalisa.gerenciadorDeTarefas.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_task")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Length(min = 5, max = 100, message = "O título deverá conter no máximo {max} caracteres.")
  private String title;

  @Length(min = 3, max = 250, message = "A descrição deve ter no máximo {max} caracteres.")
  private String description;

  private LocalDate validity;

  private String status = "aberta";
}
