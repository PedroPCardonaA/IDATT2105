package ntnu.idi.idatt2015.tokenly.backend.service;

import ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository;
import ntnu.idi.idatt2015.tokenly.backend.model.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(Profile profile) {
        profileRepository.save(profile);
        return profile;
    }

    public Optional<Profile> getProfileById(Long profileId) {
        return profileRepository.getByProfileId(profileId);
    }
}
