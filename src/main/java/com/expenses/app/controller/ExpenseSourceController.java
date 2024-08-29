package com.expenses.app.controller;

import com.expenses.app.domain.dto.common.PaginationResponseDTO;
import com.expenses.app.domain.dto.expense.ExpenseSourceRequestDTO;
import com.expenses.app.domain.dto.expense.ExpenseSourceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Expense Source Controller")
public interface ExpenseSourceController {

  @Operation(
      summary = "Creates a new expense source",
      description =
          "Creates a new expense source by passing a JSON representation of ExpenseRequestDTO",
      responses = {
        @ApiResponse(
            description = "Created",
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = ExpenseSourceResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ExpenseSourceResponseDTO> create(ExpenseSourceRequestDTO requestDTO);

  @Operation(
      summary = "Updates a expense source",
      description =
          "Updates a expense source by passing a expense ID and  a JSON representation of ExpenseSourceRequestDTO",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ExpenseSourceResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ExpenseSourceResponseDTO> update(
      Long expenseSourceId, ExpenseSourceRequestDTO requestDTO);

  @Operation(
      summary = "Gets expense sources list related to user",
      description = "Gets expense sources list related to user",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = PaginationResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<PaginationResponseDTO<ExpenseSourceResponseDTO>> list(Pageable pageable);

  @Operation(
      summary = "Gets a expense source by id",
      description = "Gets a expense source by id by passing a expense id as a path variable",
      responses = {
        @ApiResponse(
            description = "Success",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ExpenseSourceResponseDTO.class))),
        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
      })
  ResponseEntity<ExpenseSourceResponseDTO> findById(Long expenseSourceId);
}
