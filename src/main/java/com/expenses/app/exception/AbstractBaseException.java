package com.expenses.app.exception;

public abstract class AbstractBaseException extends RuntimeException {

  public AbstractBaseException(String message) {
    super(message);
  }
}
