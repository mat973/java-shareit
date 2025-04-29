package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.UserNotFoundException;


import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (!repository.containUserById(user.getId())){
            throw  new UserNotFoundException(user.getId());
        }
        return repository.updateUser(user);
    }

    @Override
    public User getUserById(Long userId) {
        if (!repository.containUserById(userId)){
            throw  new UserNotFoundException(userId);
        }
        return repository.getUserById(userId);
    }

    @Override
    public void deleteUserById(Long userId) {
        if (!repository.containUserById(userId)){
            throw  new UserNotFoundException(userId);
        }
        repository.deleteUser(userId);

    }

    @Override
    public boolean existUserById(Long userId) {
        return repository.containUserById(userId);
    }
}