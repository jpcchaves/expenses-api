package com.expenses.app.domain.dto.expense;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ExpenseResponseDTO {

  private Long id;
  private String expenseTitle;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dueDate;

  public ExpenseResponseDTO() {}

  public ExpenseResponseDTO(Long id, String expenseTitle, LocalDate dueDate) {
    this.id = id;
    this.expenseTitle = expenseTitle;
    this.dueDate = dueDate;
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
}
