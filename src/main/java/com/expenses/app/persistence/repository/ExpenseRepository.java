package com.expenses.app.persistence.repository;

import com.expenses.app.domain.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @Query(
      value =
          "SELECT e FROM Expense e WHERE e.dueDate BETWEEN :currentDate AND :nextFiveDays AND e.notificationActive = true")
  List<Expense> findExpensesDueInNextFiveDays(
      @Param("currentDate") LocalDate currentDate, @Param("nextFiveDays") LocalDate nextFiveDays);
}
