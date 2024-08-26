package com.expenses.app.helpers;

import com.expenses.app.domain.models.User;
import org.springframework.security.core.Authentication;

public interface AuthHelper {
  User getUserDetails();

  String getName();

  Authentication getAuthentication();
}
