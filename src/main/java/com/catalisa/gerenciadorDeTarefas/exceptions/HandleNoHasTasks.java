package com.catalisa.gerenciadorDeTarefas.exceptions;

public class HandleNoHasTasks extends RuntimeException{
  public HandleNoHasTasks (String message) {
    super(message);
  }
}
