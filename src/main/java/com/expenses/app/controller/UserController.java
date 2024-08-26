package com.expenses.app.controller;

import com.expenses.app.domain.dto.expense.ExpenseResponseDTO;
import com.expenses.app.domain.dto.user.UserRequestDTO;
import com.expenses.app.domain.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User Controller")
@SecurityRequirement(name = "Bearer Authentication")
public interface UserController {

  @Operation(
      summary = "Creates a new user",
      description = "Creates a new user by passing a JSON representation of UserRequestDTO",
      responses = {
        @ApiResponse(
            description = "Created",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = ExpenseResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<UserResponseDTO> create(UserRequestDTO requestDTO);

  @Operation(
      summary = "Updates a user",
      description =
          "Updates a user by passing a user ID and  a JSON representation of ExpenseRequestDTO",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ExpenseResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<UserResponseDTO> update(Long userId, UserRequestDTO requestDTO);
}
