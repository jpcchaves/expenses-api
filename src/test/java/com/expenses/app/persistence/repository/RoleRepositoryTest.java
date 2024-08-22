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

    role = new Role("ROLE_UNIT_TEST");
  }

  @DisplayName("Test given role when save should return role object")
  @Test
  void testGivenRole_WhenSave_ShouldReturnRoleObject() {

    // Given / When
    role = roleRepository.save(role);

    // Then
    assertNotNull(role);
    assertEquals("ROLE_UNIT_TEST", role.getName());
  }
}
