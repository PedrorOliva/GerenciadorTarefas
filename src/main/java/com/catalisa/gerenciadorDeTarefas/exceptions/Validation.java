package com.catalisa.gerenciadorDeTarefas.exceptions;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Validation {
  public boolean isTitleValid(String title){
    if(title == null || title.isEmpty() || title.isBlank()){
      throw new HandleValidationField("O titulo é obrigatório!");
    }
    return true;
  }

  public boolean isTittleCount(String title) {
    if(title.length() <= 5) {
      throw new HandleValidationField("O título precisa conter no mínimo 5 caracteres");
    }
    return true;
  }

  public boolean isDesciptionValid(String description) {
    if(description == null || description.isEmpty() || description.isBlank()){
      throw new HandleValidationField("A descrição é obrigatória!");
    }
    return true;
  }

  public boolean isDescriptionCount(String description) {
    if(description.length() < 3) {
      throw new HandleValidationField("A descrição precisa conter no mínimo 3 caracteres");
    }
    return true;
  }

  public boolean isValidityValid(LocalDate validity) {

    if(validity == null) {
      throw new HandleValidationField("A validade é obrigatória!");
    }
    return true;
  }


  public boolean isValidityAfter(LocalDate validity) {
    LocalDate current = LocalDate.now();

    if (validity.isBefore(current)){
      throw new HandleValidationField("A validade não pode ser inferior a data atual!");
    }
    return true;
  }

}
