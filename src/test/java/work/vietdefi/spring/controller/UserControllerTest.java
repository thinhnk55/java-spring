package work.vietdefi.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import work.vietdefi.spring.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        User user = new User(1L, "test_user", "password", "token", 1732438613155L);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test_user"));
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        User user = new User(1L, "test_user", "password", "token", 1732438613155L);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))); // Tạo người dùng trước

        mockMvc.perform(get("/api/users/{id}", user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test_user"));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        User user1 = new User(1L, "test_user1", "password", "token", 1732438613155L);
        User user2 = new User(2L, "test_user2", "password", "token", 1732438613156L);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)));
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user2)));

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)); // Kiểm tra số lượng người dùng
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        User user = new User(1L, "test_user", "password", "token", 1732438613155L);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))); // Tạo người dùng trước

        User updatedUser = new User(1L, "updated_user", "new_password", "new_token", 1732438613156L);
        mockMvc.perform(put("/api/users/{id}", user.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updated_user"));
    }

    @Test
    void deleteUser_ShouldReturnSuccessMessage() throws Exception {
        User user = new User(1L, "test_user", "password", "token", 1732438613155L);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))); // Tạo người dùng trước

        mockMvc.perform(delete("/api/users/{id}", user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().string("User with ID 1 deleted successfully."));
    }

    @Test
    void deleteUser_ShouldReturnNotFoundMessage_WhenUserDoesNotExist() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 999L)) // ID không tồn tại
                .andExpect(status().isOk())
                .andExpect(content().string("User not found."));
    }
}
