package com.expenses.app.factory.expense;

import com.expenses.app.domain.models.Expense;
import com.expenses.app.domain.models.User;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class ConcreteExpenseFactory implements ExpenseFactory {

  @Override
  public Expense buildExpense(String expenseTitle, User user, LocalDate dueDate) {

    return new Expense(expenseTitle, user, dueDate);
  }
}
