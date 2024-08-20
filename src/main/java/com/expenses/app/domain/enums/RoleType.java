package com.expenses.app.domain.enums;

public enum RoleType {
  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_TEST("ROLE_TEST");

  private final String roleType;

  RoleType(String roleType) {
    this.roleType = roleType;
  }

  public String roleType() {
    return roleType;
  }
}
