package com.expenses.app.service;

import com.expenses.app.strategy.notification.NotificationStrategy;

public interface NotificationService {

  void sendNotification(NotificationStrategy notificationStrategy) throws InterruptedException;
}
