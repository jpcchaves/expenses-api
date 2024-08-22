package com.expenses.app.strategy.notification;

import com.expenses.app.domain.models.Expense;
import com.expenses.app.persistence.repository.ExpenseRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DailyNotificationStrategy implements NotificationStrategy {

  private final ExpenseRepository expenseRepository;

  public DailyNotificationStrategy(ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
  }

  @Override
  public List<Expense> getExpensesToNotify(LocalDate now) {
    return expenseRepository.findExpensesDueSoon(now);
  }

  @Override
  public NotificationStrategyType getType() {
    return NotificationStrategyType.DAILY;
  }
}
