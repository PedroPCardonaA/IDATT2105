package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.*;
import ntnu.idi.idatt2015.tokenly.backend.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.mockito.Mockito.*;
import static org.springframework.security.core.userdetails.User.builder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserCreationController.class)
@Import(SecurityTestConfig.class)
class UserCreationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @MockBean
    private ProfileService profileService;

    private UserCreationRequest userCreationRequest;
    private String encodedPassword;
    private Profile profile;

    @BeforeEach
    void setUp() {
        userCreationRequest = new UserCreationRequest("testuser", "password123", "John", "Doe", "john.doe@example.com", Date.valueOf("1990-01-01"));
        encodedPassword = "encoded_password";
        profile = new Profile();
        profile.setUsername("testuser");
        profile.setEmail("john.doe@example.com");
        profile.setFirst_name("John");
        profile.setLast_name("Doe");
        profile.setBirthdate(Date.valueOf("1990-01-01"));
    }

    @Test
    void createUserTest() throws Exception {
        // Arrange
        when(passwordEncoder.encode(userCreationRequest.password())).thenReturn(encodedPassword);
        when(jdbcUserDetailsManager.userExists(userCreationRequest.username())).thenReturn(false);

        UserDetails userDetails = builder()
                .username(userCreationRequest.username())
                .password(encodedPassword)
                .roles("USER")
                .build();

        when(profileService.createProfile(any(Profile.class))).thenReturn(profile);

        // Act and Assert
        mockMvc.perform(post("/api/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(userCreationRequest.username()))
                .andExpect(jsonPath("$.email").value(userCreationRequest.email()))
                .andExpect(jsonPath("$.firstname").value(userCreationRequest.firstname()))
                .andExpect(jsonPath("$.lastname").value(userCreationRequest.lastname()));

        verify(passwordEncoder, times(1)).encode(userCreationRequest.password());
        verify(jdbcUserDetailsManager, times(1)).userExists(userCreationRequest.username());
        verify(jdbcUserDetailsManager, times(1)).createUser(userDetails);
        verify(profileService, times(1)).createProfile(any(Profile.class));
    }

    @Test
    void createUser_withInvalidUsername_shouldReturnBadRequest() throws Exception {
        // Set an invalid username in the UserCreationRequest
        UserCreationRequest invalidUsernameRequest = new UserCreationRequest("", userCreationRequest.password(),
                userCreationRequest.firstname(), userCreationRequest.lastname(), userCreationRequest.email(),
                userCreationRequest.birthdate());

        mockMvc.perform(post("/api/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUsernameRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_withInvalidPassword_shouldReturnBadRequest() throws Exception {
        // Set an invalid password in the UserCreationRequest
        UserCreationRequest invalidPasswordRequest = new UserCreationRequest(userCreationRequest.username(), "",
                userCreationRequest.firstname(), userCreationRequest.lastname(), userCreationRequest.email(),
                userCreationRequest.birthdate());

        mockMvc.perform(post("/api/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_withInvalidEmail_shouldReturnBadRequest() throws Exception {
        // Set an invalid email in the UserCreationRequest
        UserCreationRequest invalidEmailRequest = new UserCreationRequest(userCreationRequest.username(), userCreationRequest.password(),
                userCreationRequest.firstname(), userCreationRequest.lastname(), "invalid-email",
                userCreationRequest.birthdate());

        mockMvc.perform(post("/api/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_withExistingUsername_shouldReturnConflict() throws Exception {
        when(jdbcUserDetailsManager.userExists(userCreationRequest.username())).thenReturn(true);

        mockMvc.perform(post("/api/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void createUser_withInternalError_shouldReturnInternalServerError() throws Exception {
        when(passwordEncoder.encode(userCreationRequest.password())).thenReturn(encodedPassword);
        when(jdbcUserDetailsManager.userExists(userCreationRequest.username())).thenReturn(false);
        doThrow(new RuntimeException("Internal server error")).when(jdbcUserDetailsManager).createUser(any(UserDetails.class));

        mockMvc.perform(post("/api/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationRequest)))
                .andExpect(status().isInternalServerError());
    }
}
