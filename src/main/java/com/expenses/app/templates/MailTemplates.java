package com.expenses.app.templates;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class MailTemplates {

  private final SpringTemplateEngine templateEngine;

  public MailTemplates(SpringTemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  public String getNotifyExpenseTemplate() {

    Context context = new Context();

    String htmlTemplate = templateEngine.process("notifyExpenseTemplate", context);

    return htmlTemplate;
  }
}
