package com.expenses.app.scheduledjobs;

import com.expenses.app.domain.models.Expense;
import com.expenses.app.domain.models.User;
import com.expenses.app.persistence.repository.ExpenseRepository;
import com.expenses.app.service.MailService;
import com.expenses.app.templates.MailTemplates;
import java.time.LocalDate;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotifyExpenses {

  private static final Logger logger = LoggerFactory.getLogger(NotifyExpenses.class);
  private final ExpenseRepository expenseRepository;
  private final MailService mailService;
  private final MailTemplates mailTemplates;

  public NotifyExpenses(
      ExpenseRepository expenseRepository, MailService mailService, MailTemplates mailTemplates) {
    this.expenseRepository = expenseRepository;
    this.mailService = mailService;
    this.mailTemplates = mailTemplates;
  }

  @Scheduled(cron = "0 0 5 * * *", zone = "America/Sao_Paulo")
  @Transactional(readOnly = true)
  public void sendNotification() throws InterruptedException {

    logger.info("Starting scheduled job to notify expenses");

    LocalDate now = LocalDate.now();
    LocalDate nextFiveDays = LocalDate.now().plusDays(5);

    List<Expense> expensesToNotify =
        expenseRepository.findExpensesDueInNextFiveDays(now, nextFiveDays);

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

      Thread.sleep(2000);
    }

    logger.info("Finished scheduled job to notify expenses");
  }
}
