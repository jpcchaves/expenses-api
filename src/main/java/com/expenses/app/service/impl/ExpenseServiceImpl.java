package com.expenses.app.service.impl;

import com.expenses.app.domain.dto.common.PaginationResponseDTO;
import com.expenses.app.domain.dto.common.ResponseDTO;
import com.expenses.app.domain.dto.expense.ExpenseRequestDTO;
import com.expenses.app.domain.dto.expense.ExpenseResponseDTO;
import com.expenses.app.domain.models.Expense;
import com.expenses.app.domain.models.ExpenseSource;
import com.expenses.app.domain.models.User;
import com.expenses.app.exception.ResourceNotFoundException;
import com.expenses.app.helpers.AuthHelper;
import com.expenses.app.persistence.repository.ExpenseRepository;
import com.expenses.app.persistence.repository.ExpenseSourceRepository;
import com.expenses.app.persistence.repository.UserRepository;
import com.expenses.app.service.ExpenseService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final UserRepository userRepository;
  private final ExpenseSourceRepository expenseSourceRepository;
  private final AuthHelper authHelper;

  public ExpenseServiceImpl(
      ExpenseRepository expenseRepository,
      UserRepository userRepository,
      ExpenseSourceRepository expenseSourceRepository,
      AuthHelper authHelper) {
    this.expenseRepository = expenseRepository;
    this.userRepository = userRepository;
    this.expenseSourceRepository = expenseSourceRepository;
    this.authHelper = authHelper;
  }

  @Override
  public ExpenseResponseDTO create(ExpenseRequestDTO requestDTO) {

    User user =
        userRepository
            .findById(authHelper.getUserDetails().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

    ExpenseSource expenseSource =
        expenseSourceRepository
            .findById(requestDTO.getExpenseSourceId())
            .orElseThrow(() -> new ResourceNotFoundException("Fonte de custo não encontrado!"));

    Expense expense =
        new Expense(
            requestDTO.getExpenseTitle(),
            user,
            expenseSource,
            requestDTO.getDueDate(),
            requestDTO.getExpenseFrequency());

    expense = expenseRepository.save(expense);

    return new ExpenseResponseDTO(expense.getId(), expense.getExpenseTitle(), expense.getDueDate());
  }

  @Override
  public ExpenseResponseDTO update(Long expenseId, ExpenseRequestDTO requestDTO) {

    Expense expense =
        expenseRepository
            .findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada!"));

    User user =
        userRepository
            .findById(authHelper.getUserDetails().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

    ExpenseSource expenseSource =
        expenseSourceRepository
            .findById(requestDTO.getExpenseSourceId())
            .orElseThrow(() -> new ResourceNotFoundException("Fonte de custo não encontrado!"));

    expense.setExpenseTitle(requestDTO.getExpenseTitle());
    expense.setUser(user);
    expense.setExpenseSource(expenseSource);
    expense.setDueDate(requestDTO.getDueDate());
    expense.setExpenseFrequency(requestDTO.getExpenseFrequency());

    expense = expenseRepository.save(expense);

    return new ExpenseResponseDTO(expense.getId(), expense.getExpenseTitle(), expense.getDueDate());
  }

  @Override
  public PaginationResponseDTO<ExpenseResponseDTO> list(Pageable pageable) {

    Page<Expense> expensesPage =
        expenseRepository.findAll(pageable, authHelper.getUserDetails().getId());

    List<ExpenseResponseDTO> expenseResponseDTOList =
        expensesPage.getContent().stream()
            .map(
                expense ->
                    new ExpenseResponseDTO(
                        expense.getId(), expense.getExpenseTitle(), expense.getDueDate()))
            .toList();

    return new PaginationResponseDTO<ExpenseResponseDTO>()
        .builder()
        .setContent(expenseResponseDTOList)
        .setPage(expensesPage.getNumber())
        .setSize(expensesPage.getSize())
        .setTotalElements(expensesPage.getTotalElements())
        .setTotalPages(expensesPage.getTotalPages())
        .setLast(expensesPage.isLast())
        .build();
  }

  @Override
  public ExpenseResponseDTO findById(Long expenseId) {

    Expense expense =
        expenseRepository
            .findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada!"));

    return new ExpenseResponseDTO(expense.getId(), expense.getExpenseTitle(), expense.getDueDate());
  }

  @Override
  public ResponseDTO<?> toggleNotificationPreference(Long expenseId) {

    Expense expense =
        expenseRepository
            .findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada!"));

    Boolean prevActive = expense.getNotificationActive();

    expense.setNotificationActive(!prevActive);

    expenseRepository.save(expense);

    return ResponseDTO.withMessage(
        "Preferência de notificação atualizada para a despesa: " + expense.getExpenseTitle());
  }
}
