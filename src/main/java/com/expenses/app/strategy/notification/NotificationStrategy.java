package com.expenses.app.strategy.notification;

import com.expenses.app.domain.models.Expense;
import java.time.LocalDate;
import java.util.List;

public interface NotificationStrategy {

  List<Expense> getExpensesToNotify(LocalDate now);

  NotificationStrategyType getType();
}
