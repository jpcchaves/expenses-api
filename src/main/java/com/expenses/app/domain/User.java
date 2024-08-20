package com.expenses.app.domain;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
public class User implements Serializable {

  @Serial private static final long serialVersionUID = 4272589753813163768L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
  private Long id;

  @Column(nullable = false, length = 200)
  private String name;

  @Column(nullable = false, length = 150, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
  @JoinTable(
      name = "users_roles",
      uniqueConstraints =
          @UniqueConstraint(
              columnNames = {"user_id", "role_id"},
              name = "unique_user_role"),
      joinColumns =
          @JoinColumn(
              name = "user_id",
              referencedColumnName = "id",
              table = "users",
              foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)),
      inverseJoinColumns =
          @JoinColumn(
              name = "role_id",
              referencedColumnName = "id",
              table = "roles",
              foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
  private Set<Role> roles = new HashSet<>();

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  public User() {}

  public User(String name, String email, String password, Set<Role> roles) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  public User(
      Long id,
      String name,
      String email,
      String password,
      Set<Role> roles,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.roles = roles;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + '}';
  }
}
