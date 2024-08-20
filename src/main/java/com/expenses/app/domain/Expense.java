package com.expenses.app.domain;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "expenses")
@SequenceGenerator(name = "seq_expense", sequenceName = "seq_expense", allocationSize = 1)
public class Expense implements Serializable {

  @Serial private static final long serialVersionUID = 2802665311247124887L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_expense")
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "user_id",
      nullable = false,
      foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "user_fk"))
  private User user;

  // TODO: implement attributes
}
