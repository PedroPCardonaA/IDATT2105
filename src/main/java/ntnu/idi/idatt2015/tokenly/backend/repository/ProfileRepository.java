package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Profile;

import java.util.Optional;

public interface ProfileRepository {

    void save(Profile profile);

    Optional<Profile> getByProfileId(Long profileId);
}

