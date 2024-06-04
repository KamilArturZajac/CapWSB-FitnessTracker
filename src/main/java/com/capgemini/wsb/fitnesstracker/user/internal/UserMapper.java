package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    UseInfoListDto toUserInfoListDto(User user) {
        return new UseInfoListDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    UserEmailInfoDto toUserIdEmailDto(User user) {
        return new UserEmailInfoDto(user.getId(), user.getEmail());
    }

    UserBirthdayInfoDto toUserIdBirthdayDto(User user) {
        return new UserBirthdayInfoDto(user.getId(), user.getBirthdate());
    }

    User updateEntity(User user, com.capgemini.wsb.fitnesstracker.user.api.UserDto userDto) {
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthdate(userDto.birthdate());
        user.setEmail(userDto.email());
        return user;
    }
}
