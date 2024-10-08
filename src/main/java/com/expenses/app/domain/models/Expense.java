package com.expenses.app.domain.models;

import com.expenses.app.domain.enums.ExpenseFrequency;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(
    name = "expenses",
    indexes = {
      @Index(name = "due_date_idx", columnList = "dueDate"),
      @Index(name = "user_id_idx", columnList = "user_id"),
    })
@SequenceGenerator(name = "seq_expense", sequenceName = "seq_expense", allocationSize = 1)
public class Expense implements Serializable {

  @Serial private static final long serialVersionUID = 2802665311247124887L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_expense")
  private Long id;

  @Column(nullable = false, length = 100)
  private String expenseTitle;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "user_id",
      nullable = false,
      foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "user_fk"))
  private User user;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate dueDate;

  @Column(nullable = false)
  private Boolean notificationActive = Boolean.TRUE;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ExpenseFrequency expenseFrequency = ExpenseFrequency.MONTHLY;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  public Expense() {}

  public Expense(
      Long id,
      String expenseTitle,
      User user,
      LocalDate dueDate,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.expenseTitle = expenseTitle;
    this.user = user;
    this.dueDate = dueDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Expense(String expenseTitle, User user, LocalDate dueDate) {
    this.expenseTitle = expenseTitle;
    this.user = user;
    this.dueDate = dueDate;
  }

  public Expense(String expenseTitle, User user, LocalDate dueDate, Boolean notificationActive) {
    this.expenseTitle = expenseTitle;
    this.user = user;
    this.dueDate = dueDate;
    this.notificationActive = notificationActive;
  }

  public Expense(
      String expenseTitle,
      User user,
      LocalDate dueDate,
      Boolean notificationActive,
      ExpenseFrequency expenseFrequency) {
    this.expenseTitle = expenseTitle;
    this.user = user;
    this.dueDate = dueDate;
    this.notificationActive = notificationActive;
    this.expenseFrequency = expenseFrequency;
  }

  public Expense(
      String expenseTitle, User user, LocalDate dueDate, ExpenseFrequency expenseFrequency) {
    this.expenseTitle = expenseTitle;
    this.user = user;
    this.dueDate = dueDate;
    this.expenseFrequency = expenseFrequency;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getExpenseTitle() {
    return expenseTitle;
  }

  public void setExpenseTitle(String expenseTitle) {
    this.expenseTitle = expenseTitle;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public Boolean getNotificationActive() {
    return notificationActive;
  }

  public void setNotificationActive(Boolean notificationActive) {
    this.notificationActive = notificationActive;
  }

  public ExpenseFrequency getExpenseFrequency() {
    return expenseFrequency;
  }

  public void setExpenseFrequency(ExpenseFrequency expenseFrequency) {
    this.expenseFrequency = expenseFrequency;
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
    Expense expense = (Expense) o;
    return Objects.equals(id, expense.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Expense{"
        + "id="
        + id
        + ", expenseTitle='"
        + expenseTitle
        + '\''
        + ", dueDate="
        + dueDate
        + '}';
  }
}
