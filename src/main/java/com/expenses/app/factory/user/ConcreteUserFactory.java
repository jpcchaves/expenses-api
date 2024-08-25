package com.expenses.app.factory.user;

import com.expenses.app.domain.models.Role;
import com.expenses.app.domain.models.User;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ConcreteUserFactory implements UserFactory {

  @Override
  public User buildUser(String name, String email, String password, Set<Role> roles) {
    return new User(name, email, password, roles);
  }
}
