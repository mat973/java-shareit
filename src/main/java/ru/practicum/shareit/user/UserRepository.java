package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

interface UserRepository {
    List<User> findAll();

    User save(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    boolean containUserById(Long userid);

    boolean checkUnicEmail(String email);
}