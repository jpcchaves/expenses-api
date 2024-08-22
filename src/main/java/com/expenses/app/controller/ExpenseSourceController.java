package com.expenses.app.controller;

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
public class ExpenseSourceController {

  private final ExpenseSourceService expenseSourceService;

  public ExpenseSourceController(ExpenseSourceService expenseSourceService) {
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

  @GetMapping("/{userId}")
  public ResponseEntity<PaginationResponseDTO<ExpenseSourceResponseDTO>> list(
      @PathVariable(name = "userId") Long userId, Pageable pageable) {

    return ResponseEntity.ok(expenseSourceService.list(userId, pageable));
  }

  @GetMapping("/{expenseSourceId}")
  public ResponseEntity<ExpenseSourceResponseDTO> findById(
      @PathVariable(name = "expenseSourceId") Long expenseSourceId) {

    return ResponseEntity.ok(expenseSourceService.findById(expenseSourceId));
  }

  @GetMapping("/{userId}/{expenseSourceId}")
  public ResponseEntity<ExpenseSourceResponseDTO> findById(
      @PathVariable(name = "userId") Long userId,
      @PathVariable(name = "expenseSourceId") Long expenseSourceId) {

    return ResponseEntity.ok(expenseSourceService.findById(userId, expenseSourceId));
  }
}
