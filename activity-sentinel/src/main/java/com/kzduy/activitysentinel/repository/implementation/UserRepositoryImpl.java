package com.kzduy.activitysentinel.repository.implementation;

import com.kzduy.activitysentinel.domain.Role;
import com.kzduy.activitysentinel.domain.User;
import com.kzduy.activitysentinel.exception.ApiException;
import com.kzduy.activitysentinel.repository.RoleRepository;
import com.kzduy.activitysentinel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static com.kzduy.activitysentinel.domain.enumeration.RoleType.ROLE_USER;
import static com.kzduy.activitysentinel.domain.enumeration.VerificationType.ACCOUNT;
import static com.kzduy.activitysentinel.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        // Save new user to db
        // Check unique email?
        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0)
            throw new ApiException("Email already exist, please try again.");
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey().longValue()));
            // Add role to user
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            // Send verification url
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            // Save URL in verification table
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of("userId", user.getId(), "token", verificationUrl));
            // Send email to user with verification URL
//            emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            // Return user in4 just added
            return user;
            // Throw exception
        } catch (Exception ex){
            throw new ApiException("Something went wrong, please try again" + ex.getMessage());
        }
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email){
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }
    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type){
        //server url
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type + "/" + key).toString();
    }
}
