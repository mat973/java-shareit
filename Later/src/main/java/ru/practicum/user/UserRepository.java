package ru.practicum.user;

import java.util.List;

interface UserRepository {
    List<User> findAll();
    User save(User user);
    User updateUser(User user);
    void deleteUser(Long userId);
    User getUserById(Long userId);
}