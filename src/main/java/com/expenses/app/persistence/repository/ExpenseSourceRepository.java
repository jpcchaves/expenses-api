package com.expenses.app.persistence.repository;

import com.expenses.app.domain.ExpenseSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseSourceRepository extends JpaRepository<ExpenseSource, Long> {}
