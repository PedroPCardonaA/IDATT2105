/**
 * ProfileController handles the HTTP requests related to profile operations.
 * It exposes endpoints for updating profile fields, and retrieving profiles by ID and username.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    PasswordEncoder passwordEncoder;

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
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/profile/{profileId}/balance")
    public ResponseEntity<?> updateBalance(@PathVariable("profileId") long profileId,
                                           @RequestParam("balance") double balance) {
        try {
            log.info("User try to change the balance of a profile = " + profileId);
            if (balance < 0 || balance > 10) {
                throw new IllegalArgumentException("Balance must be between 0 and 10.");
            }

            profileRepository.updateBalance(profileId, balance);
            log.info("Balance was updated correctly");
            return ResponseEntity.ok("Balance updated successfully");
        } catch (IllegalArgumentException e) {
            log.info("Given information is not correct");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
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
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/profile/{profileId}/firstname")
    public ResponseEntity<?> updateFirstname(@PathVariable("profileId") long profileId,
                                           @RequestParam("firstname") String firstname){
        log.info("User try to change the first name of a profile = " + profileId);

        if (firstname == null || firstname.trim().isEmpty() || firstname.length() < 2 || firstname.length() > 50) {
            log.info("Given information is not correct");
            return new ResponseEntity<>("Invalid firstname value", HttpStatus.BAD_REQUEST);
        }
        try {
            profileRepository.updateFirstname(profileId, firstname);
            return ResponseEntity.ok("First name updated successfully");
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
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
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/profile/{profileId}/lastname")
    public ResponseEntity<?> updateLastname(@PathVariable("profileId") long profileId,
                                          @RequestParam("lastname") String lastname){
        log.info("User try to change the last name of a profile = " + profileId);
        if (lastname == null || lastname.trim().isEmpty() || lastname.length() < 2 || lastname.length() > 50) {
            log.info("Given information is not correct");
            return new ResponseEntity<>("Invalid firstname value", HttpStatus.BAD_REQUEST);
        }
        try {
            profileRepository.updateLastname(profileId, lastname);
            return ResponseEntity.ok("Last name updated successfully");
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
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
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/profile/{profileId}/email")
    public ResponseEntity<?> updateEmail(@PathVariable("profileId") long profileId,
                                       @RequestParam("email") String email){
        log.info("User try to change the email of a profile = " + profileId);
        if (email == null || email.trim().isEmpty() || !email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")) {
            log.info("Given information is not correct");
            return new ResponseEntity<>("Invalid email value", HttpStatus.BAD_REQUEST);
        }
        try {
            profileRepository.updateEmail(profileId, email);
            return ResponseEntity.ok("Email updated successfully");
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
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
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/profile/{profileId}/birthdate")
    public ResponseEntity<?> updateBirthdate(@PathVariable("profileId") long profileId,
                                             @RequestParam("birthdate") String birthdate) {
        try {
            log.info("User try to change the birthdate of a profile = " + profileId);
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
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
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
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<?> getProfile(@PathVariable("profileId") long profileId){
        try {
            log.info("User is trying to get a profile info by id = " +profileId);
            return ResponseEntity.ok(profileRepository.getByProfileId(profileId));
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
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
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/profile/username/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable("username") String username){
        try {
            log.info("User try to get profiles information by username = " + username);
            return ResponseEntity.ok(profileRepository.getByUsername(username));
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting profile.");
        }
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
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/profile/{profileId}/addBalance")
    public ResponseEntity<?> addBalance(@PathVariable("profileId") long profileId,
                                           @RequestParam("balance") double balance) {
        try {
            log.info("User try to change the current balance");
            if (balance < -100 || balance > 100) {
                log.info("Given information is not correct");
                throw new IllegalArgumentException("Balance must be between -10 and 10.");
            }

            int answer = profileRepository.changeBalance(profileId, balance);
            if(answer == -1){
                log.info("Given information is not correct");
                return ResponseEntity.badRequest().body("ERROR: BALANCE CANNOT BE DEFINED AS NEGATIVE");
            }
            return ResponseEntity.ok("Balance updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating balance.");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/profile/{username}/password")
    public ResponseEntity<?> changePassword(@PathVariable("username") String username,@RequestParam("newPassword") String password,@RequestParam("oldPassword") String oldPassword){
        try{
            log.info("User try to change the password of a profile");
            String olsPassword = passwordEncoder.encode(oldPassword);
            String hash = passwordEncoder.encode(password);
            int answer = profileRepository.updatePassword(username,hash,olsPassword);
            if(answer == -1) return ResponseEntity.badRequest().body("ERROR: USERNAME OR PASSWORD IS NOT CORRECT.");
            return ResponseEntity.ok(answer);
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().body("INTERNAL SERVER ERROR");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/profile/isAdmin/{username}")
    public ResponseEntity<?> isAdmin(@PathVariable("username") String username){
        try{
            log.info("A user try to know if the following user is an admin = " + username);
            Boolean answer = profileRepository.isAdmin(username);
            return ResponseEntity.ok(answer);
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().body("INTERNAL SERVER ERROR");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        try {
            log.info("A user try to get all the profiles");
            Optional<?> users = profileRepository.getAllUser();
            return ResponseEntity.ok(users.get());
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().body("INTERNAL SERVER ERROR");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/ban/{username}")
    public ResponseEntity<?> changeEnableTable(@PathVariable("username") String username){
        try {
            log.info("Admin try to ban a user.");
            long answer = profileRepository.changeUnable(username);
            if(answer==-1){
                return ResponseEntity.badRequest().body("ERROR: USERNAME OR PASSWORD IS NOT CORRECT.");
            }
            return ResponseEntity.ok(answer);
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().body("INTERNAL SERVER ERROR");
        }
    };

}
