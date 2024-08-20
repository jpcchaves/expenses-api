package com.expenses.app.domain;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "expense_sources")
@SequenceGenerator(
    name = "seq_expense_source",
    sequenceName = "seq_expense_source",
    allocationSize = 1)
public class ExpenseSource implements Serializable {

  @Serial private static final long serialVersionUID = 5843293301440651973L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_expense_source")
  private Long id;

  @Column(nullable = false)
  private String name;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  public ExpenseSource() {}

  public ExpenseSource(Long id, String name) {
    this.id = id;
    this.name = name;
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
    ExpenseSource that = (ExpenseSource) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "ExpenseSource{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
