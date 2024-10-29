package work.vietdefi.spring.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import work.vietdefi.spring.auth.dto.LoginRequest;
import work.vietdefi.spring.auth.dto.RegisterRequest;
import work.vietdefi.spring.auth.dto.UserDTO;
import work.vietdefi.spring.common.dto.BaseResponse;
import work.vietdefi.spring.common.dto.ErrorResponse;
import work.vietdefi.spring.util.json.JSONUtil;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static RegisterRequest registerRequest;
    private static LoginRequest loginRequest;
    private static LoginRequest loginRequestInvalidUsername;
    private static LoginRequest loginRequestInvalidPassword;

    private UserDTO userDTO;

    @BeforeAll
    public static void setUp() {
        String username = "test_user_" + RandomStringUtils.randomNumeric(5);
        registerRequest = new RegisterRequest(username, "password");
        loginRequest = new LoginRequest(username, "password");
        loginRequestInvalidUsername = new LoginRequest("username", "password");
        loginRequestInvalidPassword = new LoginRequest(username, "invalid_password");
    }

    @Test
    @Order(1)
    void testRegister() throws Exception {
        // Perform the POST request
        String jsonResponse = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()) // Print the response
                .andReturn()
                .getResponse()
                .getContentAsString();
        BaseResponse<UserDTO> responseUser = JSONUtil.fromJson(jsonResponse, new TypeReference<>() {});
        userDTO = responseUser.getData();
    }
    @Test
    @Order(2)
    void testRegisterAgain() throws Exception {
        // Perform the POST request
        String jsonResponse = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()) // Print the response
                .andReturn()
                .getResponse()
                .getContentAsString();
        BaseResponse<ErrorResponse> response = JSONUtil.fromJson(jsonResponse, new TypeReference<>() {});
        ErrorResponse errorResponse = response.getData();
        assertEquals("", 10, errorResponse.getErrorCode());

    }
    @Test
    @Order(3)
    void testLogin() throws Exception {
        // Perform the POST request
        String jsonResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(loginRequest)))
                .andDo(print()) // Print the response
                .andReturn()
                .getResponse()
                .getContentAsString();
        BaseResponse<UserDTO> responseUser = JSONUtil.fromJson(jsonResponse, new TypeReference<>() {});
        userDTO = responseUser.getData();
    }
    @Test
    @Order(4)
    void testLoginInvalidUserName() throws Exception {
        // Perform the POST request
        String jsonResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(loginRequestInvalidUsername)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()) // Print the response
                .andReturn()
                .getResponse()
                .getContentAsString();
        BaseResponse<ErrorResponse> response = JSONUtil.fromJson(jsonResponse, new TypeReference<>() {});
        ErrorResponse errorResponse = response.getData();
        assertEquals("", 10, errorResponse.getErrorCode());
        assertEquals("", "invalid_username", errorResponse.getMessage());

    }
    @Test
    @Order(4)
    void testLoginInvalidPassword() throws Exception {
        // Perform the POST request
        String jsonResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(loginRequestInvalidPassword)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()) // Print the response
                .andReturn()
                .getResponse()
                .getContentAsString();
        BaseResponse<ErrorResponse> response = JSONUtil.fromJson(jsonResponse, new TypeReference<>() {});
        ErrorResponse errorResponse = response.getData();
        assertEquals("", 11, errorResponse.getErrorCode());
        assertEquals("", "invalid_password", errorResponse.getMessage());
    }
}