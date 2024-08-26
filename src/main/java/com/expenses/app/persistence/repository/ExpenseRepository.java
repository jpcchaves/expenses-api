package com.expenses.app.persistence.repository;

import com.expenses.app.domain.enums.ExpenseFrequency;
import com.expenses.app.domain.models.Expense;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @Query(value = "SELECT * FROM expenses WHERE user_id = :userId", nativeQuery = true)
  Page<Expense> findAll(Pageable pageable, Long userId);

  @Query(
      value =
          "SELECT e FROM Expense e WHERE e.dueDate BETWEEN :currentDate AND :nextFiveDays AND e.notificationActive = true ORDER BY e.dueDate ASC")
  List<Expense> findExpensesDueInNextFiveDays(
      @Param("currentDate") LocalDate currentDate, @Param("nextFiveDays") LocalDate nextFiveDays);

  @Query(
      value =
          "SELECT e FROM Expense e WHERE e.dueDate BETWEEN :currentDate AND :rangeOfDays AND e.notificationActive = true  ORDER BY e.dueDate ASC")
  List<Expense> findExpensesDueInRangeOfDays(
      @Param("currentDate") LocalDate currentDate, @Param("rangeOfDays") LocalDate rangeOfDays);

  @Query(
      "SELECT e FROM Expense e WHERE e.dueDate = :currentDate AND e.notificationActive = true ORDER BY e.dueDate ASC")
  List<Expense> findExpensesDueSoon(@Param("currentDate") LocalDate currentDate);

  @Query(
      value =
          "SELECT e FROM Expense e WHERE e.expenseFrequency = :expenseFrequency AND e.notificationActive = true")
  List<Expense> findExpensesByFrequency(ExpenseFrequency expenseFrequency);

  @Query(
      value =
          "SELECT e FROM Expense e WHERE e.expenseFrequency = :expenseFrequency AND e.notificationActive = true AND e.dueDate BETWEEN :startDate AND :endDate")
  List<Expense> findExpensesByFrequencyFromPastMonth(
      LocalDate startDate, LocalDate endDate, ExpenseFrequency expenseFrequency);
}
