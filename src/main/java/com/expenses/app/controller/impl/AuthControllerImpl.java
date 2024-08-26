package com.expenses.app.controller.impl;

import com.expenses.app.controller.AuthController;
import com.expenses.app.domain.dto.auth.LoginRequestDTO;
import com.expenses.app.domain.dto.auth.LoginResponseDTO;
import com.expenses.app.domain.dto.auth.RegisterRequestDTO;
import com.expenses.app.domain.dto.common.ResponseDTO;
import com.expenses.app.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerImpl implements AuthController {

  private final AuthService authService;

  public AuthControllerImpl(AuthService authService) {
    this.authService = authService;
  }

  @Override
  @PostMapping("/login")
  public ResponseEntity<ResponseDTO<LoginResponseDTO>> login(
      @Valid @RequestBody LoginRequestDTO requestDTO) {

    return ResponseEntity.ok(authService.login(requestDTO));
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseDTO<?>> register(
      @Valid @RequestBody RegisterRequestDTO requestDTO) {

    return ResponseEntity.ok(authService.register(requestDTO));
  }
}
