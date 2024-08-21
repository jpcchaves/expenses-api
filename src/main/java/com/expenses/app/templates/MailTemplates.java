package com.expenses.app.templates;

import com.expenses.app.domain.models.Expense;
import java.util.List;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class MailTemplates {

  private final SpringTemplateEngine templateEngine;

  public MailTemplates(SpringTemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  public String getNotifyExpenseTemplate(String name, List<Expense> expenseList) {

    Context context = new Context();

    context.setVariable("name", name);
    context.setVariable("expenses", expenseList);

    String htmlTemplate = templateEngine.process("notify-expenses", context);

    return htmlTemplate;
  }
}
