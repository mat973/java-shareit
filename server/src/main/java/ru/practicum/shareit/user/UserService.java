package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto user);

    UserDto updateUser(UserDto user);

    UserDto getUserById(Long userId);

    void deleteUserById(Long userId);

    boolean existUserById(Long userId);

    Optional<User> getClearUser(Long userId);
}