package com.expenses.app.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.domain.models.Role;
import com.expenses.app.domain.models.User;
import java.util.Optional;
import java.util.Set;
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

  @Autowired private RoleRepository roleRepository;

  private Faker faker;

  private User user;
  private Role role;

  private ExpenseSource expenseSource;

  private String expenseSourceName;

  @BeforeEach
  void setup() {

    faker = new Faker();

    expenseSourceName = faker.lorem().characters(10);

    role = roleRepository.save(new Role("ROLE_TEST"));

    user =
        userRepository.save(
            new User("User test", "usertest@test.com", faker.number().digits(20), Set.of(role)));

    expenseSource =
        expenseSourceRepository.saveAndFlush(new ExpenseSource(expenseSourceName, user));
  }

  @DisplayName("Test find expense source by name")
  @Test
  void testFindExpenseSourceByName() {

    // Given / When
    Optional<ExpenseSource> expenseSource =
        expenseSourceRepository.findByName(user.getId(), expenseSourceName);

    // Then
    assertNotNull(expenseSource.get());
    assertEquals(expenseSourceName, expenseSource.get().getName());
    assertEquals(user.getId(), expenseSource.get().getUser().getId());
  }

  @DisplayName("Test find all expense sources")
  @Test
  void testFindAllExpenseSources() {

    // Given
    Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created_at"));

    // When
    Page<ExpenseSource> expenseSourcePage = expenseSourceRepository.findAll(user.getId(), pageable);

    // Then
    assertNotNull(expenseSourcePage);

    assertEquals(1, expenseSourcePage.getContent().size());
  }

  @DisplayName("Test find expense source by id")
  @Test
  void testFindExpenseSourceById() {

    // Given /  When
    ExpenseSource expenseSourceById =
        expenseSourceRepository.findById(user.getId(), expenseSource.getId()).get();

    // Then
    assertNotNull(expenseSourceById);
    assertEquals(expenseSource.getName(), expenseSourceById.getName());
    assertEquals(expenseSource.getId(), expenseSourceById.getId());
    assertEquals(user.getId(), expenseSourceById.getUser().getId());
  }
}
