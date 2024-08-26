package com.expenses.app.controller.impl;

import com.expenses.app.domain.dto.common.PaginationResponseDTO;
import com.expenses.app.domain.dto.expense.ExpenseSourceRequestDTO;
import com.expenses.app.domain.dto.expense.ExpenseSourceResponseDTO;
import com.expenses.app.service.ExpenseSourceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expense-sources")
public class ExpenseSourceControllerImpl {

  private final ExpenseSourceService expenseSourceService;

  public ExpenseSourceControllerImpl(ExpenseSourceService expenseSourceService) {
    this.expenseSourceService = expenseSourceService;
  }

  @PostMapping
  public ResponseEntity<ExpenseSourceResponseDTO> create(
      @Valid @RequestBody ExpenseSourceRequestDTO requestDTO) {

    return ResponseEntity.status(HttpStatus.CREATED).body(expenseSourceService.create(requestDTO));
  }

  @PutMapping("/{expenseSourceId}")
  public ResponseEntity<ExpenseSourceResponseDTO> update(
      @PathVariable(name = "expenseSourceId") Long expenseSourceId,
      @Valid @RequestBody ExpenseSourceRequestDTO requestDTO) {

    return ResponseEntity.ok(expenseSourceService.update(expenseSourceId, requestDTO));
  }

  @GetMapping
  public ResponseEntity<PaginationResponseDTO<ExpenseSourceResponseDTO>> list(Pageable pageable) {

    return ResponseEntity.ok(expenseSourceService.list(pageable));
  }

  @GetMapping("/{expenseSourceId}")
  public ResponseEntity<ExpenseSourceResponseDTO> findById(
      @PathVariable(name = "expenseSourceId") Long expenseSourceId) {

    return ResponseEntity.ok(expenseSourceService.findById(expenseSourceId));
  }
}
