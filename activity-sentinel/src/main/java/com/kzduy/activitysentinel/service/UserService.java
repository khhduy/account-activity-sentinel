package com.kzduy.activitysentinel.service;

import com.kzduy.activitysentinel.domain.User;
import com.kzduy.activitysentinel.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
}
