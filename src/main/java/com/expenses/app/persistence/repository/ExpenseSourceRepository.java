package com.expenses.app.persistence.repository;

import com.expenses.app.domain.models.ExpenseSource;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseSourceRepository extends JpaRepository<ExpenseSource, Long> {

  @Query(value = "SELECT * FROM expense_sources es WHERE es.name = :name", nativeQuery = true)
  Optional<ExpenseSource> findByName(String name);

  @Query(value = "SELECT * FROM expense_sources es WHERE es.user_id = :userId", nativeQuery = true)
  Page<ExpenseSource> findAll(Long userId, Pageable pageable);

  @Query(
      value =
          "SELECT * FROM expense_resources es WHERE es.user_id = :userId AND es.id = :expenseSourceId",
      nativeQuery = true)
  Optional<ExpenseSource> findById(Long userId, Long expenseSourceId);
}
