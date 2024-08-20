package com.expenses.app.domain.dto.expense;

import java.io.Serial;
import java.io.Serializable;

public class ExpenseSourceResponseDTO implements Serializable {

  @Serial private static final long serialVersionUID = -4810232243426776976L;

  private Long id;
  private String name;

  public ExpenseSourceResponseDTO() {}

  public ExpenseSourceResponseDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
