package com.expenses.app.service;

import com.expenses.app.domain.dto.UserRequestDTO;
import com.expenses.app.domain.dto.UserResponseDTO;

public interface UserService {

  UserResponseDTO create(UserRequestDTO requestDTO);

  UserResponseDTO update(Long userId, UserRequestDTO requestDTO);
}
