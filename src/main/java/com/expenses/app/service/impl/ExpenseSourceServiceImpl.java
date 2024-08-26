package com.expenses.app.service.impl;

import com.expenses.app.domain.dto.common.PaginationResponseDTO;
import com.expenses.app.domain.dto.expense.ExpenseSourceRequestDTO;
import com.expenses.app.domain.dto.expense.ExpenseSourceResponseDTO;
import com.expenses.app.domain.models.ExpenseSource;
import com.expenses.app.domain.models.User;
import com.expenses.app.exception.BadRequestException;
import com.expenses.app.exception.ResourceNotFoundException;
import com.expenses.app.persistence.repository.ExpenseSourceRepository;
import com.expenses.app.persistence.repository.UserRepository;
import com.expenses.app.service.ExpenseSourceService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ExpenseSourceServiceImpl implements ExpenseSourceService {

  private final ExpenseSourceRepository expenseSourceRepository;
  private final UserRepository userRepository;

  public ExpenseSourceServiceImpl(
      ExpenseSourceRepository expenseSourceRepository, UserRepository userRepository) {
    this.expenseSourceRepository = expenseSourceRepository;
    this.userRepository = userRepository;
  }

  @Override
  public ExpenseSourceResponseDTO create(ExpenseSourceRequestDTO requestDTO) {

    User securityContextUser =
        (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User user =
        userRepository
            .findById(securityContextUser.getId())
            .orElseThrow(() -> new BadRequestException("Usuário não encontrado!"));

    ExpenseSource expenseSource = new ExpenseSource(requestDTO.getName(), user);

    expenseSource = expenseSourceRepository.save(expenseSource);

    return new ExpenseSourceResponseDTO(expenseSource.getId(), expenseSource.getName());
  }

  @Override
  public ExpenseSourceResponseDTO update(Long expenseSourceId, ExpenseSourceRequestDTO requestDTO) {

    User securityContextUser =
        (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User user =
        userRepository
            .findById(securityContextUser.getId())
            .orElseThrow(() -> new BadRequestException("Usuário não encontrado!"));

    ExpenseSource expenseSource =
        expenseSourceRepository
            .findById(expenseSourceId)
            .orElseThrow(() -> new ResourceNotFoundException("Não encontrado!"));

    expenseSource.setName(requestDTO.getName());
    expenseSource.setUser(user);

    expenseSource = expenseSourceRepository.save(expenseSource);

    return new ExpenseSourceResponseDTO(expenseSource.getId(), expenseSource.getName());
  }

  @Override
  public PaginationResponseDTO<ExpenseSourceResponseDTO> list(Pageable pageable) {

    Page<ExpenseSource> expenseSourcePage = expenseSourceRepository.findAll(pageable);

    List<ExpenseSourceResponseDTO> sourceResponseDTOList =
        expenseSourcePage.getContent().stream()
            .map(
                expenseSource ->
                    new ExpenseSourceResponseDTO(expenseSource.getId(), expenseSource.getName()))
            .toList();

    return new PaginationResponseDTO<ExpenseSourceResponseDTO>()
        .builder()
        .setContent(sourceResponseDTOList)
        .setPage(expenseSourcePage.getNumber())
        .setSize(expenseSourcePage.getSize())
        .setTotalElements(expenseSourcePage.getTotalElements())
        .setTotalPages(expenseSourcePage.getTotalPages())
        .setLast(expenseSourcePage.isLast())
        .build();
  }

  @Override
  public PaginationResponseDTO<ExpenseSourceResponseDTO> list(Long userId, Pageable pageable) {

    Page<ExpenseSource> expenseSourcePage = expenseSourceRepository.findAll(userId, pageable);

    List<ExpenseSourceResponseDTO> sourceResponseDTOList =
        expenseSourcePage.getContent().stream()
            .map(
                expenseSource ->
                    new ExpenseSourceResponseDTO(expenseSource.getId(), expenseSource.getName()))
            .toList();

    return new PaginationResponseDTO<ExpenseSourceResponseDTO>()
        .builder()
        .setContent(sourceResponseDTOList)
        .setPage(expenseSourcePage.getNumber())
        .setSize(expenseSourcePage.getSize())
        .setTotalElements(expenseSourcePage.getTotalElements())
        .setTotalPages(expenseSourcePage.getTotalPages())
        .setLast(expenseSourcePage.isLast())
        .build();
  }

  @Override
  public ExpenseSourceResponseDTO findById(Long expenseSourceId) {

    ExpenseSource expenseSource =
        expenseSourceRepository
            .findById(expenseSourceId)
            .orElseThrow(() -> new ResourceNotFoundException("Não encontrado!"));

    return new ExpenseSourceResponseDTO(expenseSource.getId(), expenseSource.getName());
  }

  @Override
  public ExpenseSourceResponseDTO findById(Long userId, Long expenseSourceId) {

    ExpenseSource expenseSource =
        expenseSourceRepository
            .findById(userId, expenseSourceId)
            .orElseThrow(() -> new ResourceNotFoundException("Não encontrado!"));

    return new ExpenseSourceResponseDTO(expenseSource.getId(), expenseSource.getName());
  }
}
