package com.expenses.app.exception;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

  @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      Object body,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest request) {

    logger.error(ex.getMessage());
    logger.error(Arrays.toString(ex.getStackTrace()));

    ExceptionResponseDTO exceptionResponse =
        ExceptionResponseDTO.builder()
            .setMessage(ex.getMessage())
            .setDetails(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<ExceptionResponseDTO> handleAccessDeniedException(
      BadRequestException ex, WebRequest request) {

    ExceptionResponseDTO exceptionResponse =
        ExceptionResponseDTO.builder()
            .setMessage(ex.getMessage())
            .setDetails(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}
