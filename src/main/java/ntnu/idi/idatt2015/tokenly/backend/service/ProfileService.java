package ntnu.idi.idatt2015.tokenly.backend.service;

import ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository;
import ntnu.idi.idatt2015.tokenly.backend.model.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This interface represents a service for Profile objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    /**
     * Creates a new ProfileService object.
     * The repository object is injected by Spring.
     *
     * @param profileRepository the ProfileRepository object to use
     */
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Creates a new Profile object in the repository.
     *
     * @param profile the Profile object to create
     * @return the created Profile object
     */
    public Profile createProfile(Profile profile) {
        profileRepository.save(profile);
        return profile;
    }

    /**
     * Retrieves a Profile object from the repository by its ID.
     *
     * @param profileId the ID of the Profile object to retrieve
     * @return an Optional containing the Profile object, or an empty Optional if no Profile object with the specified ID exists
     */
    public Optional<Profile> getProfileById(Long profileId) {
        return profileRepository.getByProfileId(profileId);
    }
}
