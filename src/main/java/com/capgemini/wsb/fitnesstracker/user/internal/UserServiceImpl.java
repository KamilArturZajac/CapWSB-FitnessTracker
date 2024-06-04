package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(final User user) {
        log.info("New user is creating... {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public List<User> getUserByHisEmail(String email) {
        return userRepository.getUserByHisEmail(email);
    }

    @Override
    public List<User> getUserByHisBithdate(@JsonFormat(pattern = "yyyy-MM-dd") LocalDate bithdate) {
        return userRepository.getUserByHisBirthday(bithdate);
    }

    @Override
    public List<User> getUsersBornLaterThan(LocalDate time) {
        return userRepository.getUsersBornLaterThan(time);
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("User is deleting {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        log.info("User is updating {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        User updatedUser = userMapper.updateEntity(user, userDto);

        return userRepository.save(updatedUser);
    }
}