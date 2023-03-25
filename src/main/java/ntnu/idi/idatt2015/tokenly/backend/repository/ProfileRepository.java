package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Profile;

import java.sql.Date;
import java.util.Optional;

public interface ProfileRepository {

    void save(Profile profile);

    Optional<Profile> getByProfileId(Long profileId);

    Optional<Profile> getByUsername(String username);

    int updateBalance(long profileId, double balance);

    int updateFirstname(long profileId, String firstname);

    int updateLastname(long profileId, String lastname);

    int updateEmail(long profileId, String email);

    int updateBirthdate(long profileId, Date birthdate);
}

