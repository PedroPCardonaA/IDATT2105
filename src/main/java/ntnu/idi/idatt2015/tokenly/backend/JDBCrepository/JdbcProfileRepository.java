package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Profile;
import ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcProfileRepository implements ProfileRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcProfileRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void save(Profile profile) {
        String sql = "INSERT INTO profiles (username, first_name, last_name, birthdate) " +
                "VALUES(:username, :first_name, :last_name, :birthdate)";
        Map<String, Object> params = new HashMap<>();
        params.put("username", profile.getUsername());
        params.put("first_name", profile.getFirstname());
        params.put("last_name", profile.getLastname());
        params.put("birthdate", profile.getBirthdate());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Profile> getByProfileId(Long profileId) {
        String sql = "SELECT * FROM profiles WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", profileId);
        try {
            Profile profile = namedParameterJdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Profile.class));
            return Optional.ofNullable(profile);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }
}
