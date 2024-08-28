package com.expenses.app.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.domain.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest extends AbstractTestContainerConfig {

  @Autowired private RoleRepository roleRepository;

  private Role role;

  @BeforeEach
  void setup() {

    role = roleRepository.save(new Role("ROLE_TEST"));
  }

  @DisplayName("Test save role")
  @Test
  void testSaveRole() {

    assertNotNull(role);
    assertEquals("ROLE_TEST", role.getName());
  }

  @DisplayName("Test find role by name")
  @Test
  void testFindRoleByName() {

    Role roleByName = roleRepository.findByName(role.getName()).get();

    assertNotNull(roleByName);
    assertEquals("ROLE_TEST", role.getName());
  }
}
