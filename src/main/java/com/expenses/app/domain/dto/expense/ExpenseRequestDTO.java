package com.expenses.app.domain.dto.expense;

import com.expenses.app.domain.enums.ExpenseFrequency;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ExpenseRequestDTO {

  @NotBlank(message = "O título é obrigatório!")
  private String expenseTitle;

  @NotNull(message = "A fonte da despesa não pode ser nula!")
  @Min(value = 1, message = "A fonte da despesa não pode ser nula!")
  private Long expenseSourceId;

  @FutureOrPresent(message = "A data precisa ser hoje ou futura!")
  private LocalDate dueDate;

  private ExpenseFrequency expenseFrequency;

  public ExpenseRequestDTO() {}

  public ExpenseRequestDTO(String expenseTitle, Long expenseSourceId, LocalDate dueDate) {
    this.expenseTitle = expenseTitle;
    this.expenseSourceId = expenseSourceId;
    this.dueDate = dueDate;
  }

  public ExpenseRequestDTO(
      String expenseTitle,
      Long expenseSourceId,
      LocalDate dueDate,
      ExpenseFrequency expenseFrequency) {
    this.expenseTitle = expenseTitle;
    this.expenseSourceId = expenseSourceId;
    this.dueDate = dueDate;
    this.expenseFrequency = expenseFrequency;
  }

  public String getExpenseTitle() {
    return expenseTitle;
  }

  public void setExpenseTitle(String expenseTitle) {
    this.expenseTitle = expenseTitle;
  }

  public Long getExpenseSourceId() {
    return expenseSourceId;
  }

  public void setExpenseSourceId(Long expenseSourceId) {
    this.expenseSourceId = expenseSourceId;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public ExpenseFrequency getExpenseFrequency() {
    return expenseFrequency;
  }

  public void setExpenseFrequency(ExpenseFrequency expenseFrequency) {
    this.expenseFrequency = expenseFrequency;
  }
}
