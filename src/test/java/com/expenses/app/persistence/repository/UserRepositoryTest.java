package com.expenses.app.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.domain.models.Role;
import com.expenses.app.domain.models.User;
import java.util.Set;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractTestContainerConfig {

  private final Faker faker = new Faker();
  private final String USER_EMAIL = faker.internet().emailAddress();
  @Autowired private UserRepository userRepository;
  @Autowired private RoleRepository roleRepository;
  private User user;
  private Role role;

  @BeforeEach
  void setup() {

    role = roleRepository.save(new Role("ROLE_TEST"));

    user =
        userRepository.save(
            new User("User test", USER_EMAIL, faker.number().digits(20), Set.of(role)));
  }

  @DisplayName("Test find by email")
  @Test
  void testFindByEmail() {

    User userByEmail = userRepository.findByEmail(USER_EMAIL).get();

    assertNotNull(userByEmail);
    assertEquals(USER_EMAIL, userByEmail.getEmail());
  }

  @DisplayName("Test exists by email")
  @Test
  void testExistsByEmail() {

    Boolean exists = userRepository.existsByEmail(USER_EMAIL);

    assertTrue(exists);
  }

  @DisplayName("Test save user")
  @Test
  void testSaveUser() {
    final String USER_NAME = faker.name().name();
    final String USER_PASSWORD = faker.number().digits(20);

    User userToSave = new User(USER_NAME, USER_EMAIL, USER_PASSWORD, Set.of(role));

    User savedUser = userRepository.save(userToSave);

    assertNotNull(savedUser);
    assertTrue(savedUser.getId() > 0);
    assertEquals(USER_NAME, savedUser.getName());
    assertEquals(USER_EMAIL, savedUser.getEmail());
    assertEquals(USER_PASSWORD, savedUser.getPassword());
  }
}
