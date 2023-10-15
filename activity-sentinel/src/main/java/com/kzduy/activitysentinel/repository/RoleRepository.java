package com.kzduy.activitysentinel.repository;

import com.kzduy.activitysentinel.domain.Role;
import com.kzduy.activitysentinel.domain.User;

import java.util.Collection;

public interface RoleRepository<T extends Role>{
    //    Basic CRUD
    T Create(T data);
    //Read all with pagination
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    //Complex operations
    void addRoleToUser(Long userId, String roleName);

    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);

}
