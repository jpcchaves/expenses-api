package com.expenses.app.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.config.TestsConfigConstants;
import com.expenses.app.domain.dto.user.UserRequestDTO;
import com.expenses.app.domain.dto.user.UserResponseDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest extends AbstractTestContainerConfig {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static Faker faker;
  private static String TEST_EMAIL;
  private static String TEST_NAME;
  private static String TEST_PASSWORD;
  private static RequestSpecification requestSpecification;
  private static UserRequestDTO userRequestDTO;
  @Autowired private UserController userController;

  @BeforeAll
  static void setup() {

    faker = new Faker();

    TEST_EMAIL = faker.internet().emailAddress();

    TEST_NAME = faker.name().fullName();

    TEST_PASSWORD = faker.number().digits(12);

    userRequestDTO = new UserRequestDTO(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, TEST_PASSWORD);

    requestSpecification =
        new RequestSpecBuilder()
            .setBasePath("/api/v1/users")
            .setPort(TestsConfigConstants.SERVER_PORT)
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();

    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    mapper.registerModule(new JavaTimeModule());
  }

  @DisplayName("Integration test given user request dto when save should return user response DTO")
  @Test
  void integrationTestGivenUserRequestDTO_WhenSave_ShouldReturnUserResponseDTO()
      throws IOException {

    String response =
        given()
            .spec(requestSpecification)
            .contentType(TestsConfigConstants.CONTENT_TYPE_JSON)
            .body(userRequestDTO)
            .when()
            .post()
            .then()
            .extract()
            .body()
            .asString();

    UserResponseDTO responseDTO = mapper.readValue(response, UserResponseDTO.class);

    assertNotNull(responseDTO);
  }
}
