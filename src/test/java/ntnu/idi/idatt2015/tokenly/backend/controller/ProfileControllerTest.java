package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Profile;
import ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@Import(SecurityTestConfig.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private Profile profile;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        profile = new Profile();
        profile.setId(1L);
        profile.setUsername("user1");
        profile.setFirst_name("John");
        profile.setLast_name("Doe");
        profile.setEmail("john.doe@example.com");
        profile.setBirthdate(Date.valueOf(LocalDate.of(2000, 1, 1)));
        profile.setBalance(5.0);
    }

    @Test
    void updateBalance_validBalance_shouldReturnOk() throws Exception {
        long profileId = profile.getId();
        double newBalance = 8.0;

        when(profileRepository.updateBalance(profileId, newBalance)).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/balance", profileId)
                        .param("balance", String.valueOf(newBalance)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Balance updated successfully"));
    }

    @Test
    void updateBalance_invalidBalance_shouldReturnBadRequest() throws Exception {
        long profileId = profile.getId();
        double newBalance = -8.0;

        when(profileRepository.updateBalance(profileId, newBalance)).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/balance", profileId)
                        .param("balance", String.valueOf(newBalance)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateFirstname_validFirstname_shouldReturnOk() throws Exception {
        long profileId = profile.getId();
        String newFirstname = "UpdatedFirstName";

        when(profileRepository.updateFirstname(profileId, newFirstname)).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/firstname", profileId)
                        .param("firstname", newFirstname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("First name updated successfully"));
    }

    @Test
    void updateFirstname_invalidFirstname_shouldReturnBadRequest() throws Exception {
        long profileId = profile.getId();
        String newFirstname = "a";

        when(profileRepository.updateFirstname(profileId, newFirstname)).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/firstname", profileId)
                        .param("firstname", newFirstname))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateLastname_validLastname_shouldReturnOk() throws Exception {
        long profileId = profile.getId();
        String newLastname = "UpdatedLastName";

        when(profileRepository.updateLastname(profileId, newLastname)).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/lastname", profileId)
                        .param("lastname", newLastname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Last name updated successfully"));
    }

    @Test
    void updateLastname_invalidLastname_shouldReturnBadRequest() throws Exception {
        long profileId = profile.getId();
        String newLastname = "a";

        when(profileRepository.updateLastname(profileId, newLastname)).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/lastname", profileId)
                        .param("lastname", newLastname))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEmail_validEmail_shouldReturnOk() throws Exception {
        long profileId = profile.getId();
        String newEmail = "updated_email@example.com";

        when(profileRepository.updateEmail(profileId, newEmail)).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/email", profileId)
                        .param("email", newEmail))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Email updated successfully"));
    }

    @Test
    void updateEmail_invalidEmail_shouldReturnBadRequest() throws Exception {
        long profileId = profile.getId();
        String newEmail = "updated_emailexample.com";

        when(profileRepository.updateEmail(profileId, newEmail)).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/email", profileId)
                        .param("email", newEmail))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBirthdate_validBirthdate_shouldReturnOk() throws Exception {
        long profileId = profile.getId();
        String newBirthdate = "2000-01-01";

        when(profileRepository.updateBirthdate(profileId, Date.valueOf(newBirthdate))).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/birthdate", profileId)
                        .param("birthdate", newBirthdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Birthdate updated successfully"));
    }

    @Test
    void updateBirthdate_invalidBirthdate_shouldReturnBadRequest() throws Exception {
        long profileId = profile.getId();
        String newBirthdate = "1337-01-01";

        when(profileRepository.updateBirthdate(profileId, Date.valueOf(newBirthdate))).thenReturn(1);

        mockMvc.perform(put("/api/profiles/profile/{profileId}/birthdate", profileId)
                        .param("birthdate", newBirthdate))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProfile_validId_shouldReturnProfile() throws Exception {
        long profileId = profile.getId();

        when(profileRepository.getByProfileId(profileId)).thenReturn(Optional.of(profile));

        mockMvc.perform(get("/api/profiles/profile/{profileId}", profileId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(profile.getId()))
                .andExpect(jsonPath("$.username").value(profile.getUsername()))
                .andExpect(jsonPath("$.first_name").value(profile.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(profile.getLast_name()))
                .andExpect(jsonPath("$.email").value(profile.getEmail()))
                .andExpect(jsonPath("$.birthdate").value(profile.getBirthdate().toString()))
                .andExpect(jsonPath("$.balance").value(profile.getBalance()));
    }

    @Test
    void getProfileByUsername_validUsername_shouldReturnProfile() throws Exception {
        String username = profile.getUsername();

        when(profileRepository.getByUsername(username)).thenReturn(Optional.of(profile));

        mockMvc.perform(get("/api/profiles/profile/username/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(profile.getId()))
                .andExpect(jsonPath("$.username").value(profile.getUsername()))
                .andExpect(jsonPath("$.first_name").value(profile.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(profile.getLast_name()))
                .andExpect(jsonPath("$.email").value(profile.getEmail()))
                .andExpect(jsonPath("$.birthdate").value(profile.getBirthdate().toString()))
                .andExpect(jsonPath("$.balance").value(profile.getBalance()));
    }
}
