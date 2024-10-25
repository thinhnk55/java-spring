package work.vietdefi.spring.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import work.vietdefi.spring.model.User;
import work.vietdefi.spring.util.json.JSONUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static User testUser;

    @BeforeAll
    public static void setUp() {
        testUser = new User(null, "test_user", "password", null, null); // userId is null for auto-generation
    }

    @Test
    @Order(1)
    void testCreateUser() throws Exception {
        // Perform the POST request
        String jsonResponse = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSONUtil.toJson(testUser)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print()) // Print the response
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Convert the JSON response to a User object
        User responseUser = JSONUtil.fromJson(jsonResponse, User.class);

        // Assertions to validate the response
        assertNotNull(responseUser.getUserId()); // Ensure the ID is not null (auto-generated)
        assertEquals("test_user", responseUser.getUsername()); // Ensure the username matches
        assertEquals(testUser.getUsername(), responseUser.getUsername()); // Check that the username is correctly assigned

        // Update the test user to the response user for further testing
        testUser = responseUser;
    }
    @Test
    @Order(2)
    void testGetUser() throws Exception {
        String jsonResponse = mockMvc.perform(get("/api/users/" + testUser.getUserId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()) // Print the response
                .andReturn()
                .getResponse()
                .getContentAsString();
        // Convert the JSON response to a User object
        User responseUser = JSONUtil.fromJson(jsonResponse, User.class);
        assertNotNull(responseUser.getUserId()); // Ensure the ID is not null (auto-generated)

    }
    @Test
    @Order(3)
    void testUpdateUser() throws Exception {
        testUser.setToken("token");
        testUser.setTokenExpired(System.currentTimeMillis());
        String jsonResponse = mockMvc.perform(put("/api/users/" + testUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONUtil.toJson(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print()) // Print the response
                .andReturn()
                .getResponse()
                .getContentAsString();
        // Convert the JSON response to a User object
        User responseUser = JSONUtil.fromJson(jsonResponse, User.class);
        assertNotNull(responseUser.getUserId()); // Ensure the ID is not null (auto-generated)
        testUser = responseUser;
    }
    @Test
    @Order(4)
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + testUser.getUserId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    @Test
    @Order(5)
    void testGetNotFoundUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + testUser.getUserId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }
}
