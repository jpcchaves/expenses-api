package com.expenses.app.service;

import com.expenses.app.domain.dto.common.PaginationResponseDTO;
import com.expenses.app.domain.dto.expense.ExpenseRequestDTO;
import com.expenses.app.domain.dto.expense.ExpenseResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

  ExpenseResponseDTO create(ExpenseRequestDTO requestDTO);

  ExpenseResponseDTO update(Long expenseId, ExpenseRequestDTO requestDTO);

  PaginationResponseDTO<ExpenseResponseDTO> list(Pageable pageable);

  ExpenseResponseDTO findById(Long expenseId);
}
