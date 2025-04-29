package ru.practicum.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUser(User user);
    User getUserById(Long userId);
    void deleteUserById(Long userId);
    boolean existUserById(Long userId);
}