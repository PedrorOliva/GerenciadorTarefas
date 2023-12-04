package com.catalisa.gerenciadorDeTarefas.exception;

import com.catalisa.gerenciadorDeTarefas.exceptions.HandleValidationField;
import com.catalisa.gerenciadorDeTarefas.exceptions.Validation;
import org.junit.jupiter.api.DisplayName;
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

  @DisplayName("should validate if there is a title")
  @Test
  public void testIsTitleValid() {
    assertTrue(validation.isTitleValid("Titulo"));
  }

  @DisplayName("should validate if there is no title")
  @Test
  public void testIsTitleInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isTitleValid(null));
  }

  @DisplayName("should validate that the title contains at least 5 characters")
  @Test
  public void testIsTitleCountValid() {
    assertTrue(validation.isTittleCount("Titulo"));
  }

  @DisplayName("should validate if the title contains less than 5 characters")
  @Test
  public void testIsTitleCountInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isTittleCount("titu"));
  }

  @DisplayName("should validate if there is a description")
  @Test
  public void testIsDescriptionValid() {
    assertTrue(validation.isDesciptionValid("descrição"));
  }

  @DisplayName("should validate if there is no description")
  @Test
  public void testIsDescriptionInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isDesciptionValid(null));
  }

  @DisplayName("should validate if the description is valid")
  @Test
  public void testIsDescriptionCountValid() {
    assertTrue(validation.isDescriptionCount("descrição"));
  }

  @DisplayName("should validate if the description contains less than 3 characters")
  @Test
  public void testIsDescriptionCountInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isDescriptionCount("de"));
  }

  @DisplayName("should validate if there is an expiration date")
  @Test
  public void testIsValidityValid() {
    assertTrue(validation.isValidityValid(LocalDate.now().plusDays(1)));
  }

  @DisplayName("should validate if the expiration date is null")
  @Test
  public void testIsValidityInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isValidityValid(null));
  }

  @DisplayName("should validate if the expiration date is later than the current date")
  @Test
  public void testIsValidityAfterValid() {
    assertTrue(validation.isValidityAfter(LocalDate.now().plusDays(1)));
  }

  @DisplayName("should validate if the expiration date is lower than the current date")
  @Test
  public void testIsValidityAfterInvalid() {
    assertThrows(HandleValidationField.class, () -> validation.isValidityAfter(LocalDate.now().minusDays(1)));
  }
}
