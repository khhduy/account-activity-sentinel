package com.kzduy.activitysentinel.service.implementation;

import com.kzduy.activitysentinel.domain.User;
import com.kzduy.activitysentinel.dto.UserDTO;
import com.kzduy.activitysentinel.dtomapper.UserDTOMapper;
import com.kzduy.activitysentinel.repository.UserRepository;
import com.kzduy.activitysentinel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;

    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
