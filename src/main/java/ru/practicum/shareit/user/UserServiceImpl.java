package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.practicum.shareit.exception.NotUnicEmailException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        return repository.findAll().stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        checkUnicEmail(userDto.getEmail());
        return UserMapper.toUserDto(repository.save(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (!repository.containUserById(userDto.getId())) {
            throw new UserNotFoundException(userDto.getId());
        }
        if (userDto.getEmail() != null) {
            checkUnicEmail(userDto.getEmail());
        }
        return UserMapper.toUserDto(repository.updateUser(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto getUserById(Long userId) {
        if (!repository.containUserById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return UserMapper.toUserDto(repository.getUserById(userId));
    }

    @Override
    public void deleteUserById(Long userId) {
        if (!repository.containUserById(userId)) {
            throw new UserNotFoundException(userId);
        }
        repository.deleteUser(userId);

    }

    @Override
    public boolean existUserById(Long userId) {
        return repository.containUserById(userId);
    }

    public void checkUnicEmail(String email) {
        if (repository.findAll().stream().map(User::getEmail).anyMatch(em -> em.equals(email))) {
            throw new NotUnicEmailException("email " + email + " уже занять другим пользователем");
        }
    }
}