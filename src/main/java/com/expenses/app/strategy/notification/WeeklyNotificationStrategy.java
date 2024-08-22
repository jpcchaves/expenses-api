package com.expenses.app.strategy.notification;

import com.expenses.app.domain.models.Expense;
import com.expenses.app.persistence.repository.ExpenseRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class WeeklyNotificationStrategy implements NotificationStrategy {

  private final ExpenseRepository expenseRepository;

  public WeeklyNotificationStrategy(ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
  }

  @Override
  public List<Expense> getExpensesToNotify(LocalDate now) {

    LocalDate weekRange = now.plusDays(4);

    return expenseRepository.findExpensesDueInRangeOfDays(now, weekRange);
  }

  @Override
  public NotificationStrategyType getType() {
    return NotificationStrategyType.WEEKLY;
  }
}
