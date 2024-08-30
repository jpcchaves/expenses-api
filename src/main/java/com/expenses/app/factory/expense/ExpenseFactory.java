package com.expenses.app.factory.expense;

import com.expenses.app.domain.models.Expense;
import com.expenses.app.domain.models.User;
import java.time.LocalDate;

public interface ExpenseFactory {

  Expense buildExpense(String expenseTitle, User user, LocalDate dueDate);
}
