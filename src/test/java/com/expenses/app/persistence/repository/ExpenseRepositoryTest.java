package com.expenses.app.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.domain.models.Expense;
import com.expenses.app.domain.models.ExpenseSource;
import com.expenses.app.domain.models.User;
import java.time.LocalDate;
import java.util.List;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpenseRepositoryTest extends AbstractTestContainerConfig {

  @Autowired private ExpenseRepository expenseRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ExpenseSourceRepository expenseSourceRepository;

  private Faker faker;

  private User user;
  private ExpenseSource expenseSource;

  @BeforeEach
  void setupEach() {
    faker = new Faker();

    user = userRepository.findByEmail("test@test.com").get();

    expenseSource = expenseSourceRepository.findByName(user.getId(), "Fatura do Cartao").get();

    List<Expense> mockExpensesList =
        List.of(
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().plusDays(1)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().plusDays(2)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().plusDays(3)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().plusDays(4)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().plusDays(5)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().plusDays(6)));

    expenseRepository.saveAll(mockExpensesList);
  }

  @Test
  void findExpensesDueInNextFiveDays() {

    LocalDate now = LocalDate.now();
    LocalDate nextFiveDays = LocalDate.now().plusDays(5);
    List<Expense> expenseList = expenseRepository.findExpensesDueInNextFiveDays(now, nextFiveDays);

    assertNotNull(expenseList);
  }
}
