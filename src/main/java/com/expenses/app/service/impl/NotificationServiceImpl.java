package com.expenses.app.service.impl;

import com.expenses.app.domain.models.Expense;
import com.expenses.app.domain.models.User;
import com.expenses.app.service.MailService;
import com.expenses.app.service.NotificationService;
import com.expenses.app.strategy.notification.NotificationStrategy;
import com.expenses.app.templates.MailTemplates;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

  private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
  private final MailService mailService;
  private final MailTemplates mailTemplates;

  public NotificationServiceImpl(MailService mailService, MailTemplates mailTemplates) {
    this.mailService = mailService;
    this.mailTemplates = mailTemplates;
  }

  @Override
  @Transactional(readOnly = true)
  public void sendNotification(NotificationStrategy notificationStrategy)
      throws InterruptedException {

    LocalDate now = LocalDate.now();

    List<Expense> expensesToNotify = notificationStrategy.getExpensesToNotify(now);

    if (expensesToNotify.isEmpty()) {

      logger.info("No expenses to notify, skipping schedule...");

      return;
    }

    Map<User, List<Expense>> userExpensesMap = new HashMap<>();

    for (Expense expense : expensesToNotify) {

      User user = expense.getUser();

      userExpensesMap.computeIfAbsent(user, k -> new ArrayList<>()).add(expense);
    }

    for (Map.Entry<User, List<Expense>> entry : userExpensesMap.entrySet()) {

      User user = entry.getKey();

      List<Expense> userExpenses = entry.getValue();

      mailService.sendEmail(
          "Seu lembrete diário :)!",
          mailTemplates.getNotifyExpenseTemplate(user.getName(), userExpenses),
          user.getEmail());

      Thread.sleep(2000); // Throttle emails to avoid spamming
    }
  }
}
