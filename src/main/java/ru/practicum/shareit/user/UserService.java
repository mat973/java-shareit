package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto user);

    UserDto updateUser(UserDto user);

    UserDto getUserById(Long userId);

    void deleteUserById(Long userId);

    boolean existUserById(Long userId);
}