package com.expenses.app.exception;

import java.io.Serial;

public class InternalServerError extends AbstractBaseException {

  @Serial private static final long serialVersionUID = 5979944622353705529L;

  public InternalServerError(String message) {
    super(message);
  }
}
