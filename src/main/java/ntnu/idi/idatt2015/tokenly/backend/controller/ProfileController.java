/**
 * ProfileController handles the HTTP requests related to profile operations.
 * It exposes endpoints for updating profile fields, and retrieving profiles by ID and username.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;

    /**
     * Constructs a ProfileController with the given ProfileRepository.
     * The ProfileRepository is autowired by Spring BOOT.
     *
     * @param profileRepository the repository for accessing profile data
     */
    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Updates the balance of the profile with the given ID and returns a response entity
     * indicating the result.
     * The balance must be between 0 and 10.
     *
     * @param profileId the ID of the profile to update
     * @param balance the new balance value
     * @return ResponseEntity indicating the result of the update
     */
    @PutMapping("/profile/{profileId}/balance")
    public ResponseEntity<?> updateBalance(@PathVariable("profileId") long profileId,
                                           @RequestParam("balance") double balance) {
        try {
            if (balance < 0 || balance > 10) {
                throw new IllegalArgumentException("Balance must be between 0 and 10.");
            }

            profileRepository.updateBalance(profileId, balance);
            return ResponseEntity.ok("Balance updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating balance.");
        }
    }

    /**
     * Updates the firstname of the profile with the given ID and returns a response entity
     * indicating the result.
     * Input validation is performed on the firstname.
     *
     * @param profileId the ID of the profile to update
     * @param firstname the new firstname value
     * @return ResponseEntity indicating the result of the update
     */
    @PutMapping("/profile/{profileId}/firstname")
    public ResponseEntity<?> updateFirstname(@PathVariable("profileId") long profileId,
                                           @RequestParam("firstname") String firstname){
        if (firstname == null || firstname.trim().isEmpty() || firstname.length() < 2 || firstname.length() > 50) {
            return new ResponseEntity<>("Invalid firstname value", HttpStatus.BAD_REQUEST);
        }
        try {
            profileRepository.updateFirstname(profileId, firstname);
            return ResponseEntity.ok("First name updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating first name.");
        }
    }

    /**
     * Updates the lastname of the profile with the given ID and returns a response entity
     * indicating the result.
     * Input validation is performed on the lastname.
     *
     * @param profileId the ID of the profile to update
     * @param lastname the new lastname value
     * @return ResponseEntity indicating the result of the update
     */
    @PutMapping("/profile/{profileId}/lastname")
    public ResponseEntity<?> updateLastname(@PathVariable("profileId") long profileId,
                                          @RequestParam("lastname") String lastname){
        if (lastname == null || lastname.trim().isEmpty() || lastname.length() < 2 || lastname.length() > 50) {
            return new ResponseEntity<>("Invalid firstname value", HttpStatus.BAD_REQUEST);
        }
        try {
            profileRepository.updateLastname(profileId, lastname);
            return ResponseEntity.ok("Last name updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating last name.");
        }
    }

    /**
     * Updates the email of the profile with the given ID and returns a response entity
     * indicating the result.
     * Input validation is performed on the email.
     *
     * @param profileId the ID of the profile to update
     * @param email the new email value
     * @return ResponseEntity indicating the result of the update
     */
    @PutMapping("/profile/{profileId}/email")
    public ResponseEntity<?> updateEmail(@PathVariable("profileId") long profileId,
                                       @RequestParam("email") String email){
        if (email == null || email.trim().isEmpty() || !email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")) {
            return new ResponseEntity<>("Invalid email value", HttpStatus.BAD_REQUEST);
        }
        try {
            profileRepository.updateEmail(profileId, email);
            return ResponseEntity.ok("Email updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating email.");
        }
    }

    /**
     * Updates the birthdate of the profile with the given ID and returns a response entity
     * indicating the result.
     * Input validation is performed on the birthdate.
     *
     * @param profileId the ID of the profile to update
     * @param birthdate the new birthdate value
     * @return ResponseEntity indicating the result of the update
     */
    @PutMapping("/profile/{profileId}/birthdate")
    public ResponseEntity<?> updateBirthdate(@PathVariable("profileId") long profileId,
                                             @RequestParam("birthdate") String birthdate) {
        try {
            Date birthdateValue;
            try {
                birthdateValue = Date.valueOf(birthdate);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid birthdate format.");
            }

            LocalDate today = LocalDate.now();
            LocalDate localBirthdate = birthdateValue.toLocalDate();
            if (localBirthdate.isAfter(today) || localBirthdate.isBefore(today.minusYears(120))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid birthdate value.");
            }

            profileRepository.updateBirthdate(profileId, birthdateValue);
            return ResponseEntity.ok("Birthdate updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating birthdate.");
        }
    }

    /**
     * Retrieves the profile with the given ID and returns a response entity
     * containing the profile, or an error response if there's an issue.
     *
     * @param profileId the ID of the profile to retrieve
     * @return ResponseEntity containing the profile, or an error response
     */
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<?> getProfile(@PathVariable("profileId") long profileId){
        try {
            return ResponseEntity.ok(profileRepository.getByProfileId(profileId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting profile.");
        }
    }

    /**
     * Retrieves the profile with the given username and returns a response entity
     * containing the profile, or an error response if there's an issue.
     *
     * @param username the username of the profile to retrieve
     * @return ResponseEntity containing the profile, or an error response
     */
    @GetMapping("/profile/username/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable("username") String username){
        try {
            return ResponseEntity.ok(profileRepository.getByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting profile.");
        }
    }
}
