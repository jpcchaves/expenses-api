package com.expenses.app.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.domain.models.ExpenseSource;
import com.expenses.app.domain.models.User;
import java.util.Optional;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpenseSourceRepositoryTest extends AbstractTestContainerConfig {

  @Autowired private ExpenseSourceRepository expenseSourceRepository;

  @Autowired private UserRepository userRepository;

  private Faker faker;

  private User user;

  private ExpenseSource expenseSource;

  private String expenseSourceName;

  @BeforeEach
  void setup() {

    faker = new Faker();

    expenseSourceName = faker.lorem().characters(10);

    user = userRepository.findByEmail("test@test.com").get();

    expenseSource =
        expenseSourceRepository.saveAndFlush(new ExpenseSource(expenseSourceName, user));
  }

  @DisplayName("Test given userId and Name when findByName then should return ExpenseSource object")
  @Test
  void testGivenUserIdAndName_WhenFindByName_ThenShouldReturnExpenseSourceObject() {

    // Given / When
    Optional<ExpenseSource> expenseSource =
        expenseSourceRepository.findByName(user.getId(), expenseSourceName);

    // Then
    assertNotNull(expenseSource.get());
    assertEquals(expenseSourceName, expenseSource.get().getName());
    assertEquals(user.getId(), expenseSource.get().getUser().getId());
  }

  @DisplayName("Test given userId and Pageable when find all should return Page of ExpenseSource")
  @Test
  void testGivenUserIdAndPageableWhenFindAllShouldReturnPageObject() {

    // Given
    Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created_at"));

    // When
    Page<ExpenseSource> expenseSourcePage = expenseSourceRepository.findAll(user.getId(), pageable);

    // Then
    assertNotNull(expenseSourcePage);

    assertEquals(2, expenseSourcePage.getContent().size());
  }
}
