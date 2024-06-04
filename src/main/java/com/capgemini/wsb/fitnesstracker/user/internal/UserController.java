package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<UseInfoListDto> getAllUsersInfoList() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toUserInfoListDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User with EMAIL=%s was not found".formatted(email)));
    }

    @GetMapping("/email")
    public List<UserEmailInfoDto> getUserByHisEmail(@RequestParam String email) {
        return userService.getUserByHisEmail(email)
                .stream()
                .map(userMapper::toUserIdEmailDto)
                .toList();
    }

    @GetMapping("/bithdate")
    public List<UserBirthdayInfoDto> getUserByHisBithdate(@RequestParam @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthday) {
        return userService.getUserByHisBithdate(birthday)
                .stream()
                .map(userMapper::toUserIdBirthdayDto)
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUsersBornLaterThan(@PathVariable LocalDate time) {
        return userService.getUsersBornLaterThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createNewUser(@RequestBody UserDto userDto) {
        return userService.createUser(userMapper.toEntity(userDto));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody com.capgemini.wsb.fitnesstracker.user.api.UserDto userDto) {
        return userService.updateUser(id, userDto);
    }
}