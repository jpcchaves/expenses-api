package com.expenses.app.domain.dto.expense;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequestDTO {

  @NotBlank(message = "O título é obrigatório!")
  private String expenseTitle;

  @NotNull(message = "O usuario não pode ser nulo!")
  @Min(value = 1, message = "O usuario não pode ser nulo!")
  private Long userId;

  @NotNull(message = "A fonte da despesa não pode ser nula!")
  @Min(value = 1, message = "A fonte da despesa não pode ser nula!")
  @NotBlank
  private Long expenseSourceId;

  @FutureOrPresent(message = "A data precisa ser hoje ou futura!")
  private LocalDate dueDate;

  public ExpenseRequestDTO() {}

  public ExpenseRequestDTO(
      String expenseTitle, Long userId, Long expenseSourceId, LocalDate dueDate) {
    this.expenseTitle = expenseTitle;
    this.userId = userId;
    this.expenseSourceId = expenseSourceId;
    this.dueDate = dueDate;
  }

  public String getExpenseTitle() {
    return expenseTitle;
  }

  public void setExpenseTitle(String expenseTitle) {
    this.expenseTitle = expenseTitle;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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
}
