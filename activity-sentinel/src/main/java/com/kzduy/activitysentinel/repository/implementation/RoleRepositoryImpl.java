package com.kzduy.activitysentinel.repository.implementation;

import com.kzduy.activitysentinel.domain.Role;
import com.kzduy.activitysentinel.exception.ApiException;
import com.kzduy.activitysentinel.repository.RoleRepository;
import com.kzduy.activitysentinel.rowmapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static com.kzduy.activitysentinel.domain.enumeration.RoleType.ROLE_USER;
import static com.kzduy.activitysentinel.domain.enumeration.VerificationType.ACCOUNT;
import static com.kzduy.activitysentinel.query.RoleQuery.INSERT_ROLE_TO_USER;
import static com.kzduy.activitysentinel.query.RoleQuery.SELECT_ROLE_BY_NAME_QUERY;
import static com.kzduy.activitysentinel.query.UserQuery.INSERT_ACCOUNT_VERIFICATION_URL_QUERY;
import static com.kzduy.activitysentinel.query.UserQuery.INSERT_USER_QUERY;
import static java.util.Map.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {
    private final NamedParameterJdbcTemplate jdbc;
    @Override
    public Role Create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);
        try {
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("name", roleName), new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER,  of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException ex) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        } catch (Exception ex){
            throw new ApiException("Something went wrong, please try again" + ex.getMessage());
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
