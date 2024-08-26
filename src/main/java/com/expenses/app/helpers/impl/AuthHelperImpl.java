package com.expenses.app.helpers.impl;

import com.expenses.app.domain.models.User;
import com.expenses.app.helpers.AuthHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelperImpl implements AuthHelper {

  @Override
  public Authentication getAuthentication() {

    return SecurityContextHolder.getContext().getAuthentication();
  }

  @Override
  public User getUserDetails() {

    return (User) getAuthentication().getPrincipal();
  }

  @Override
  public String getName() {

    return getUserDetails().getName();
  }
}
