package com.expenses.app.persistence.repository;

import com.expenses.app.domain.models.ExpenseSource;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseSourceRepository extends JpaRepository<ExpenseSource, Long> {

  @Query(value = "SELECT * FROM expense_sources u WHERE u.name = :name", nativeQuery = true)
  Optional<ExpenseSource> findByName(String name);
}
