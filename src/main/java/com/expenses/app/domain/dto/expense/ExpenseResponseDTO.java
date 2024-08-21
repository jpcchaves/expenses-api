package com.expenses.app.domain.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseResponseDTO {

  private Long id;
  private String expenseTitle;
  private LocalDate dueDate;
  private BigDecimal amount;

  public ExpenseResponseDTO() {}

  public ExpenseResponseDTO(Long id, String expenseTitle, LocalDate dueDate, BigDecimal amount) {
    this.id = id;
    this.expenseTitle = expenseTitle;
    this.dueDate = dueDate;
    this.amount = amount;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getExpenseTitle() {
    return expenseTitle;
  }

  public void setExpenseTitle(String expenseTitle) {
    this.expenseTitle = expenseTitle;
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
