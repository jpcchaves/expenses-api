package com.expenses.app.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import com.expenses.app.config.AbstractTestContainerConfig;
import com.expenses.app.config.TestsConfigConstants;
import com.expenses.app.domain.dto.user.UserRequestDTO;
import com.expenses.app.domain.dto.user.UserResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Disabled
class UserControllerImplTest extends AbstractTestContainerConfig {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static Faker faker;
  private static String TEST_EMAIL;
  private static String TEST_NAME;
  private static String TEST_PASSWORD;
  private static RequestSpecification requestSpecification;
  private static UserRequestDTO userRequestDTO;

  @BeforeAll
  static void setup() {

    faker = new Faker();

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

  @BeforeEach
  void setupEach() {

    TEST_EMAIL = faker.internet().emailAddress();

    TEST_NAME = faker.name().fullName();

    TEST_PASSWORD = faker.number().digits(12);

    userRequestDTO = new UserRequestDTO(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, TEST_PASSWORD);
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

    assertTrue(responseDTO.getId() > 0);

    assertEquals(userRequestDTO.getName(), responseDTO.getName());
    assertEquals(userRequestDTO.getEmail(), responseDTO.getEmail());
  }

  @DisplayName(
      "Given a User ID and User Request DTO when updating the user it should return a User Response DTO")
  @Test
  void integrationTestGivenUserIdAndUserRequestDTO_WhenUpdateUser_ShouldReturnUserResponseDTO()
      throws JsonProcessingException {

    String postResponse =
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

    UserResponseDTO responseDTO = mapper.readValue(postResponse, UserResponseDTO.class);

    UserRequestDTO updateRequestDTO =
        new UserRequestDTO("Updated Name", "updatedemail@test.com", "123456", "123456");

    String putResponse =
        given()
            .spec(requestSpecification)
            .contentType(TestsConfigConstants.CONTENT_TYPE_JSON)
            .body(updateRequestDTO)
            .when()
            .put("/{userId}", responseDTO.getId())
            .then()
            .extract()
            .body()
            .asString();

    UserResponseDTO updateResponseDTO = mapper.readValue(putResponse, UserResponseDTO.class);

    assertNotNull(updateResponseDTO);

    assertEquals(updateRequestDTO.getName(), updateResponseDTO.getName());
    assertEquals(updateRequestDTO.getEmail(), updateResponseDTO.getEmail());
  }
}
