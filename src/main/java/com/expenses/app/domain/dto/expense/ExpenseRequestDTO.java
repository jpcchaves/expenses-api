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

  @NotNull(message = "O valor não pode ser nulo!")
  @PositiveOrZero(message = "O valor não pode ser negativo!")
  @Min(value = 0, message = "O valor deve ser maior ou igual a zero!")
  @Digits(
      integer = 10,
      fraction = 2,
      message = "O valor deve ter no máximo 10 dígitos inteiros e 2 dígitos fracionários")
  private BigDecimal amount;

  public ExpenseRequestDTO() {}

  public ExpenseRequestDTO(
      String expenseTitle,
      Long userId,
      Long expenseSourceId,
      LocalDate dueDate,
      BigDecimal amount) {
    this.expenseTitle = expenseTitle;
    this.userId = userId;
    this.expenseSourceId = expenseSourceId;
    this.dueDate = dueDate;
    this.amount = amount;
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

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
