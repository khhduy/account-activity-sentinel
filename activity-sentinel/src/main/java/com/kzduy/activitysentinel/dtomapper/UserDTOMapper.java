package com.kzduy.activitysentinel.dtomapper;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.kzduy.activitysentinel.domain.User;
import com.kzduy.activitysentinel.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    public static UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
    public static User toUser(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
