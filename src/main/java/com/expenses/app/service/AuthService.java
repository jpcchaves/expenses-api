package com.expenses.app.service;

import com.expenses.app.domain.dto.auth.LoginRequestDTO;
import com.expenses.app.domain.dto.auth.LoginResponseDTO;
import com.expenses.app.domain.dto.auth.RegisterRequestDTO;
import com.expenses.app.domain.dto.common.ResponseDTO;

public interface AuthService {

  ResponseDTO<?> register(RegisterRequestDTO requestDTO);

  ResponseDTO<LoginResponseDTO> login(LoginRequestDTO requestDTO);
}
