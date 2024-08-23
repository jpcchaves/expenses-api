package com.expenses.app.scheduled;

import com.expenses.app.domain.enums.ExpenseFrequency;
import com.expenses.app.domain.models.Expense;
import com.expenses.app.factory.expense.ExpenseFactory;
import com.expenses.app.persistence.repository.ExpenseRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpensesGenerate {

  private static final Logger logger = LoggerFactory.getLogger(ExpensesGenerate.class);
  private final ExpenseRepository expenseRepository;
  private final ExpenseFactory expenseFactory;

  public ExpensesGenerate(ExpenseRepository expenseRepository, ExpenseFactory expenseFactory) {
    this.expenseRepository = expenseRepository;
    this.expenseFactory = expenseFactory;
  }

  @Scheduled(cron = "0 0 0 1 * *", zone = "America/Sao_Paulo")
  private void generateMonthlyExpenses() {

    List<Expense> montlyExpensesList =
        expenseRepository.findExpensesByFrequency(ExpenseFrequency.MONTHLY);

    if (montlyExpensesList.isEmpty()) {

      logger.info("No monthly expenses to generate, skipping job...");

      return;
    }

    List<Expense> updatedMonthlyExpenses = new ArrayList<>();

    for (Expense expense : montlyExpensesList) {

      LocalDate updatedDueDate = expense.getDueDate().plusMonths(1);

      updatedMonthlyExpenses.add(
          expenseFactory.buildExpense(
              expense.getExpenseTitle(),
              expense.getUser(),
              expense.getExpenseSource(),
              updatedDueDate));
    }

    expenseRepository.saveAll(updatedMonthlyExpenses);
  }
}
