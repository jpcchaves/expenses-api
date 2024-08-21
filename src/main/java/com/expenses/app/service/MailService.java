package com.expenses.app.service;

import java.util.concurrent.CompletableFuture;

public interface MailService {

  CompletableFuture<Boolean> sendEmail(String subject, String body, String recipient);
}
