package com.expenses.app.service;

import com.expenses.app.domain.dto.PaginationResponseDTO;
import com.expenses.app.domain.dto.expense.ExpenseSourceRequestDTO;
import com.expenses.app.domain.dto.expense.ExpenseSourceResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ExpenseSourceService {

  ExpenseSourceResponseDTO create(ExpenseSourceRequestDTO requestDTO);

  ExpenseSourceResponseDTO update(Long expenseSourceId, ExpenseSourceRequestDTO requestDTO);

  PaginationResponseDTO<ExpenseSourceResponseDTO> list(Pageable pageable);

  ExpenseSourceResponseDTO findById(Long expenseSourceId);
}
