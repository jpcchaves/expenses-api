package com.expenses.app.controller.impl;

import com.expenses.app.controller.ExpenseController;
import com.expenses.app.domain.dto.common.PaginationResponseDTO;
import com.expenses.app.domain.dto.common.ResponseDTO;
import com.expenses.app.domain.dto.expense.ExpenseRequestDTO;
import com.expenses.app.domain.dto.expense.ExpenseResponseDTO;
import com.expenses.app.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseControllerImpl implements ExpenseController {

  private final ExpenseService expenseService;

  public ExpenseControllerImpl(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @Override
  @PostMapping
  public ResponseEntity<ExpenseResponseDTO> create(
      @Valid @RequestBody ExpenseRequestDTO requestDTO) {

    return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.create(requestDTO));
  }

  @Override
  @PutMapping("/{expenseId}")
  public ResponseEntity<ExpenseResponseDTO> update(
      @PathVariable(name = "expenseId") Long expenseId,
      @Valid @RequestBody ExpenseRequestDTO requestDTO) {

    return ResponseEntity.ok(expenseService.update(expenseId, requestDTO));
  }

  @Override
  @GetMapping
  public ResponseEntity<PaginationResponseDTO<ExpenseResponseDTO>> list(Pageable pageable) {

    return ResponseEntity.ok(expenseService.list(pageable));
  }

  @Override
  @GetMapping("/{expenseId}")
  public ResponseEntity<ExpenseResponseDTO> findById(
      @PathVariable(name = "expenseId") Long expenseId) {

    return ResponseEntity.ok(expenseService.findById(expenseId));
  }

  @Override
  @PatchMapping("/{expenseId}")
  public ResponseEntity<ResponseDTO<?>> toggleNotificationPreference(
      @PathVariable(name = "expenseId") Long expenseId) {

    return ResponseEntity.ok(expenseService.toggleNotificationPreference(expenseId));
  }
}
