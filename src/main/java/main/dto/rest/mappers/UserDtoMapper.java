package main.dto.rest.mappers;


import main.dto.rest.UserDto;
import main.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Collectors;

public class UserDtoMapper {

    public static User mapToEntity(UserDto userDto){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        if (userDto.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPatronymic(userDto.getPatronymic());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEnabled(userDto.isEnabled());
        user.setChecked(userDto.isChecked());
        user.setRoles(userDto.getRoles().stream().map(RoleDtoMapper::mapToEntity).collect(Collectors.toSet()));
        return user;
    }

    public static UserDto mapToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setPatronymic(user.getPatronymic());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setChecked(user.isChecked());
        userDto.setEnabled(user.isEnabled());
        userDto.setRoles(user.getRoles().stream().map(RoleDtoMapper::mapToDto).collect(Collectors.toSet()));
        return userDto;
    }


}
