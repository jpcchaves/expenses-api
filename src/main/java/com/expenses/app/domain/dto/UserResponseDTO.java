package com.expenses.app.domain.dto;

public class UserResponseDTO {

  private Long id;

  private String name;

  private String email;

  public UserResponseDTO() {}

  public UserResponseDTO(Long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public Long id() {
    return id;
  }

  public UserResponseDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public String name() {
    return name;
  }

  public UserResponseDTO setName(String name) {
    this.name = name;
    return this;
  }

  public String email() {
    return email;
  }

  public UserResponseDTO setEmail(String email) {
    this.email = email;
    return this;
  }
}
