package com.expenses.app.service;

import com.expenses.app.domain.dto.user.UserRequestDTO;
import com.expenses.app.domain.dto.user.UserResponseDTO;

public interface UserService {

  UserResponseDTO create(UserRequestDTO requestDTO);

  UserResponseDTO update(Long userId, UserRequestDTO requestDTO);
}
