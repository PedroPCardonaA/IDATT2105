package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.LoginRequest;
import ntnu.idi.idatt2015.tokenly.backend.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityTestConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void testToken_Success() throws Exception {
        LoginRequest userLogin = new LoginRequest("username", "password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenService.generateToken(authentication)).thenReturn("sampleToken");

        mockMvc.perform(post("/api/users/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogin)))
                .andExpect(status().isOk())
                .andExpect(content().string("sampleToken"));
    }

    @Test
    @WithMockUser
    public void testToken_InvalidCredentials() throws Exception {
        LoginRequest userLogin = new LoginRequest("username", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new AuthenticationException("Invalid username or password.") {});

        mockMvc.perform(post("/api/users/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogin)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Error: Invalid username or password."));
    }

    @Test
    public void testToken_InvalidInput() throws Exception {
        LoginRequest userLogin = new LoginRequest("", "");

        mockMvc.perform(post("/api/users/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLogin)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Invalid input."));
    }
}
