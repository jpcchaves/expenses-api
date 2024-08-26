package com.expenses.app.controller.impl;

import com.expenses.app.domain.dto.user.UserRequestDTO;
import com.expenses.app.domain.dto.user.UserResponseDTO;
import com.expenses.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerImpl {

  private final UserService userService;

  public UserControllerImpl(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO requestDTO) {

    return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(requestDTO));
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserResponseDTO> update(
      @PathVariable(name = "userId") Long userId, @Valid @RequestBody UserRequestDTO requestDTO) {

    return ResponseEntity.ok(userService.update(userId, requestDTO));
  }
}
