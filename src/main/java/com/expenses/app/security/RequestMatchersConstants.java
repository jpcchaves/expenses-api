package com.expenses.app.security;

import java.util.List;

public class RequestMatchersConstants {

  public static final String[] PUBLIC_REQUEST_MATCHERS =
      new String[] {"/swagger-ui/**", "/api/v1/auth/login", "/api/v1/auth/register"};
}
