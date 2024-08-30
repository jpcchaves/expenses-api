package com.expenses.app.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.domain.enums.ExpenseFrequency;
import com.expenses.app.domain.models.Expense;
import com.expenses.app.domain.models.Role;
import com.expenses.app.domain.models.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

  @Autowired private RoleRepository roleRepository;

  private Faker faker;

  private User user;
  private ExpenseSource expenseSource;

  @BeforeEach
  void setupEach() {
    faker = new Faker();

    Role role = roleRepository.saveAndFlush(new Role("TEST_ROLE"));

    user = userRepository.saveAndFlush(new User("Test", "test@test.com", "123456", Set.of(role)));

    expenseSource =
        expenseSourceRepository.saveAndFlush(new ExpenseSource("Fatura do Cartao", user));

    List<Expense> yearlyExpenses =
        List.of(
            new Expense(
                faker.lorem().characters(20),
                user,
                expenseSource,
                LocalDate.now().plusDays(1),
                Boolean.TRUE,
                ExpenseFrequency.YEARLY));

    List<Expense> monthlyExpenses =
        List.of(
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

    List<Expense> previousMonthMonthlyExpenses =
        List.of(
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().minusMonths(1)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().minusMonths(1)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().minusMonths(1)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().minusMonths(1)),
            new Expense(
                faker.lorem().characters(20), user, expenseSource, LocalDate.now().minusMonths(1)));

    List<Expense> mockExpensesList =
        Stream.of(yearlyExpenses, monthlyExpenses, previousMonthMonthlyExpenses)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    expenseRepository.saveAll(mockExpensesList);
  }

  @Test
  void findExpensesDueInNextFiveDays() {

    LocalDate now = LocalDate.now();
    LocalDate nextFiveDays = LocalDate.now().plusDays(5);
    List<Expense> expenseList = expenseRepository.findExpensesDueInNextFiveDays(now, nextFiveDays);

    assertNotNull(expenseList);
  }

  @Test
  void findMonthlyExpenses() {

    List<Expense> monthlyExpenses =
        expenseRepository.findExpensesByFrequency(ExpenseFrequency.MONTHLY);

    assertNotNull(monthlyExpenses);
    assertEquals(10, monthlyExpenses.size());
  }

  @Test
  void findMonthlyExpensesFromPreviousMonth() {

    LocalDate now = LocalDate.now();

    LocalDate startDate = now.minusMonths(1).withDayOfMonth(1);
    LocalDate endDate = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());

    List<Expense> previousMonthExpenses =
        expenseRepository.findExpensesByFrequencyFromPastMonth(
            startDate, endDate, ExpenseFrequency.MONTHLY);

    assertNotNull(previousMonthExpenses);
    assertEquals(5, previousMonthExpenses.size());
  }
}
