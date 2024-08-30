package com.expenses.app.service;

import com.expenses.app.domain.dto.user.UserRequestDTO;
import com.expenses.app.domain.dto.user.UserResponseDTO;
import com.expenses.app.domain.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserResponseDTO update(Long userId, UserRequestDTO requestDTO);

  User getUserByEmail(String emamil);
}
