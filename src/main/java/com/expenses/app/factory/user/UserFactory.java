package com.expenses.app.factory.user;

import com.expenses.app.domain.models.Role;
import com.expenses.app.domain.models.User;
import java.util.Set;

public interface UserFactory {

  User buildUser(String name, String email, String password, Set<Role> roles);
}
