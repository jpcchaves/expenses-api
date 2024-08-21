package com.expenses.app.domain.dto.expense;

import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

public class ExpenseSourceRequestDTO implements Serializable {

  @Serial private static final long serialVersionUID = 5415458004322916542L;

  @NotBlank(message = "O nome é obrigatório!")
  private String name;

  public ExpenseSourceRequestDTO() {}

  public ExpenseSourceRequestDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
