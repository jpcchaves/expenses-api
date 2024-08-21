package com.expenses.app.service.impl;

import com.expenses.app.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

  private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
  private final JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String from;

  public MailServiceImpl(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  @Async
  public CompletableFuture<Boolean> sendEmail(String subject, String body, String recipient) {

      MimeMessage message = javaMailSender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(message);

      try {

          helper.setFrom(from);
          helper.setTo(recipient);
          helper.setSubject(subject);
          helper.setText(body, true);

          javaMailSender.send(message);

          return CompletableFuture.completedFuture(true);

      } catch (MessagingException e) {

          logger.error("An error occurred while sending the message", e);

          return CompletableFuture.completedFuture(false);
      }
  }
}
