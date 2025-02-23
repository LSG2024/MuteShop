package muteshop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import muteshop.models.User;
import muteshop.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Clear existing users in DB if needed (Optional)
        userService.deleteAllUsers();

        // Create and save a test user before running login tests
        testUser = new User();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword(passwordEncoder.encode("password123")); // Hashed password

        userService.registerUser(testUser);
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Create a login request
        User loginRequest = new User();
        loginRequest.setEmail("testuser@example.com");
        loginRequest.setPassword("password123"); // Correct password

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.userId").exists()) // Check if userId is returned
                .andReturn();

        System.out.println("Login Response: " + result.getResponse().getContentAsString());
    }

    @Test
    void testLoginFailure_WrongPassword() throws Exception {
        User loginRequest = new User();
        loginRequest.setEmail("testuser@example.com");
        loginRequest.setPassword("wrongpassword"); // Incorrect password

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()) // 401 Unauthorized
                .andExpect(jsonPath("$.error").value("Invalid credentials"));
    }

    @Test
    void testLoginFailure_NonExistentUser() throws Exception {
        User loginRequest = new User();
        loginRequest.setEmail("nonexistent@example.com");
        loginRequest.setPassword("password123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized()) // 401 Unauthorized
                .andExpect(jsonPath("$.error").value("Invalid credentials"));
    }
}
