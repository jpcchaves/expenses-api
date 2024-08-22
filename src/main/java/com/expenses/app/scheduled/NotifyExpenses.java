package com.expenses.app.scheduled;

import com.expenses.app.service.NotificationService;
import com.expenses.app.strategy.notification.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotifyExpenses {

  private static final Logger logger = LoggerFactory.getLogger(NotifyExpenses.class);

  private final NotificationService notificationService;

  private final NotificationStrategyMapper notificationStrategyMapper;

  public NotifyExpenses(
      NotificationService notificationService,
      NotificationStrategyMapper notificationStrategyMapper) {
    this.notificationService = notificationService;
    this.notificationStrategyMapper = notificationStrategyMapper;
  }

  /*
   *
   *  Sends the notification email if the user has any expense in the next 5 days of the week
   *  It runs every monday of the month
   *
   */
  @Scheduled(cron = "0 0 5 * * MON", zone = "America/Sao_Paulo")
  public void sendWeeklyNotification() {

    logger.info("Starting scheduled job to notify weekly expenses");

    NotificationStrategy weeklyStrategy =
        notificationStrategyMapper.getStrategy(NotificationStrategyType.WEEKLY);

    try {

      notificationService.sendNotification(weeklyStrategy);

    } catch (InterruptedException ex) {

      logger.error("An error occurred sending the weekly notification");
    }

    logger.info("Finished scheduled job to notify weekly expenses");
  }

  /*
   *
   *  Sends the notification email if the user has any expense in the current day
   *  It runs every day at 5 AM
   *
   */
  @Scheduled(cron = "0 0 5 * * *", zone = "America/Sao_Paulo")
  @Transactional(readOnly = true)
  public void sendDueSoonNotification() {

    logger.info("Starting scheduled job to notify daily expenses");

    NotificationStrategy dailyStrategy =
        notificationStrategyMapper.getStrategy(NotificationStrategyType.DAILY);

    try {

      notificationService.sendNotification(dailyStrategy);
    } catch (InterruptedException ex) {

      logger.error("An error occurred sending the daily notification");
    }

    logger.info("Finished scheduled job to notify daily expenses");
  }
}
