package com.expenses.app.controller;

import com.expenses.app.domain.dto.auth.LoginRequestDTO;
import com.expenses.app.domain.dto.auth.LoginResponseDTO;
import com.expenses.app.domain.dto.auth.RegisterRequestDTO;
import com.expenses.app.domain.dto.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth Controller", description = "Controller to manage auth routes")
public interface AuthController {

  @Operation(
      summary = "Authenticates an user",
      description = "Authenticate an user using the provided username/email and password",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<LoginResponseDTO>> login(LoginRequestDTO requestDTO);

  @Operation(
      summary = "Register an user",
      description = "Register an user using the JSON representation of the registration",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ResponseDTO<?>> register(@Valid @RequestBody RegisterRequestDTO requestDTO);
}
