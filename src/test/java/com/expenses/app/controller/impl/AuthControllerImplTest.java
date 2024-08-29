package com.expenses.app.controller.impl;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.config.TestsConfigConstants;
import com.expenses.app.domain.dto.auth.LoginRequestDTO;
import com.expenses.app.domain.dto.auth.LoginResponseDTO;
import com.expenses.app.domain.dto.auth.RegisterRequestDTO;
import com.expenses.app.domain.dto.common.ResponseDTO;
import com.expenses.app.domain.models.Role;
import com.expenses.app.exception.ExceptionResponseDTO;
import com.expenses.app.persistence.repository.RoleRepository;
import com.expenses.app.security.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerImplTest extends AbstractTestContainerConfig {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final Faker faker = new Faker();
  private static final String USER_NAME = faker.name().name();
  private static final String USER_EMAIL = faker.internet().emailAddress();
  private static final String USER_PASSWORD = faker.number().digits(20);
  private static final String USER_CONFIRM_PASSWORD = USER_PASSWORD;
  private static RequestSpecification requestSpecification;
  private static RegisterRequestDTO registerRequestDTO;
  @Autowired private RoleRepository roleRepository;
  @Autowired private JwtUtils jwtUtils;

  @BeforeAll
  static void setUpAll() {

    requestSpecification =
        new RequestSpecBuilder()
            .setBasePath("/api/v1/auth")
            .setPort(TestsConfigConstants.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    mapper.registerModule(new JavaTimeModule());

    registerRequestDTO =
        new RegisterRequestDTO(USER_NAME, USER_EMAIL, USER_PASSWORD, USER_CONFIRM_PASSWORD);
  }

  @DisplayName("Test register user")
  @Test
  @Order(1)
  void testRegisterUser() throws JsonProcessingException {

    String expectedSuccessMessage = "Usuário cadastrado com sucesso!";

    // Default role that is set in service
    roleRepository.save(new Role("ROLE_USER"));

    Response response =
        given()
            .spec(requestSpecification)
            .contentType(TestsConfigConstants.CONTENT_TYPE_JSON)
            .body(registerRequestDTO)
            .when()
            .post("/register");

    ResponseDTO<?> responseDTO = mapper.readValue(response.asString(), ResponseDTO.class);

    assertNotNull(responseDTO);
    assertEquals(expectedSuccessMessage, responseDTO.getMessage());
  }

  @DisplayName("Test login user")
  @Test
  @Order(2)
  void testLoginUser() throws JsonProcessingException {

    String expectedSuccessMessage = "Usuário autenticado com sucesso!";

    LoginRequestDTO loginRequestDTO =
        new LoginRequestDTO(registerRequestDTO.getEmail(), registerRequestDTO.getPassword());

    Response response =
        given()
            .spec(requestSpecification)
            .contentType(TestsConfigConstants.CONTENT_TYPE_JSON)
            .body(loginRequestDTO)
            .when()
            .post("login");

    ResponseDTO<LoginResponseDTO> loginResponseDTO =
        mapper.readValue(response.asString(), new TypeReference<>() {});

    assertNotNull(loginResponseDTO);
    assertEquals(expectedSuccessMessage, loginResponseDTO.getMessage());
    assertEquals(registerRequestDTO.getEmail(), loginResponseDTO.getData().getUser().getEmail());
    assertEquals(registerRequestDTO.getName(), loginResponseDTO.getData().getUser().getName());
    assertTrue(jwtUtils.isTokenValid(loginResponseDTO.getData().getAccessToken()));
  }

  @DisplayName("Test register unsuccessful duplicated email")
  @Test
  @Order(3)
  void testRegisterUnsuccessfulDuplicatedEmail() throws JsonProcessingException {

    Response response =
        given()
            .spec(requestSpecification)
            .contentType(TestsConfigConstants.CONTENT_TYPE_JSON)
            .body(registerRequestDTO)
            .when()
            .post("/register");

    ExceptionResponseDTO exceptionResponseDTO =
        mapper.readValue(response.asString(), ExceptionResponseDTO.class);

    assertEquals(
        "Já existe um usuário cadastrado com o email informado!",
        exceptionResponseDTO.getMessage());

    assertEquals(400, response.statusCode());
  }
}
