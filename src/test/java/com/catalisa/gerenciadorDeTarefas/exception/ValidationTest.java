package com.catalisa.gerenciadorDeTarefas.exception;

import com.catalisa.gerenciadorDeTarefas.exceptions.HandleValidationField;
import com.catalisa.gerenciadorDeTarefas.exceptions.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ValidationTest {

  @InjectMocks
  private Validation validation;

  @Test
  public void testIsTitleValid() {
    assertTrue(validation.isTitleValid("Titulo"));
  }

  @Test
  public void testIsTitleInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isTitleValid(null));
  }

  @Test
  public void testIsTitleCountValid() {
    assertTrue(validation.isTittleCount("Titulo"));
  }

  @Test
  public void testIsTitleCountInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isTittleCount("titu"));
  }

  @Test
  public void testIsDescriptionValid() {
    assertTrue(validation.isDesciptionValid("descrição"));
  }

  @Test
  public void testIsDescriptionInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isDesciptionValid(null));
  }

  @Test
  public void testIsDescriptionCountValid() {
    assertTrue(validation.isDescriptionCount("descrição"));
  }

  @Test
  public void testIsDescriptionCountInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isDescriptionCount("de"));
  }

  @Test
  public void testIsValidityValid() {
    assertTrue(validation.isValidityValid(LocalDate.now().plusDays(1)));
  }

  @Test
  public void testIsValidityInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isValidityValid(null));
  }

  @Test
  public void testIsValidityAfterValid() {
    assertTrue(validation.isValidityAfter(LocalDate.now().plusDays(1)));
  }

  @Test
  public void testIsValidityAfterInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isValidityAfter(LocalDate.now().minusDays(1)));
  }
}
