package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Profile;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcProfileRepository implements ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcProfileRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void save(Profile profile) {
        String sql = "INSERT INTO PROFILES (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDATE) VALUES(:username, :first_name, :last_name, :email, :birthdate)";
        Map<String, Object> params = new HashMap<>();
        params.put("username", profile.getUsername());
        params.put("first_name", profile.getFirst_name());
        params.put("last_name", profile.getLast_name());
        params.put("email", profile.getEmail());
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

    @Override
    public Optional<Profile> getByUsername(String username) {
        String sql = "SELECT * FROM profiles WHERE username = :username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            Profile profile = namedParameterJdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Profile.class));
            return Optional.ofNullable(profile);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int updateBalance(long profileId, double balance) {
        String sql = "UPDATE profiles SET balance = :balance WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("balance", balance);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public int updateFirstname(long profileId, String firstname) {
        String sql = "UPDATE profiles SET first_name = :first_name WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("first_name", firstname);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public int updateLastname(long profileId, String lastname) {
        String sql = "UPDATE profiles SET last_name = :last_name WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("last_name", lastname);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public int updateEmail(long profileId, String email) {
        String sql = "UPDATE profiles SET email = :email WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("email", email);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public int updateBirthdate(long profileId, Date birthdate) {
        String sql = "UPDATE profiles SET birthdate = :birthdate WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("birthdate", birthdate);
        return namedParameterJdbcTemplate.update(sql, params);
    }
}
