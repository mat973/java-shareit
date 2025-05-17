package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.practicum.shareit.exception.NotUnicEmailException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

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
        if (!repository.existById(userDto.getId())) {
            throw new UserNotFoundException(userDto.getId());
        }
        if (userDto.getEmail() != null) {
            checkUnicEmail(userDto.getEmail());
        }
        return UserMapper.toUserDto(repository.save(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto getUserById(Long userId) {
        if (!repository.existById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return UserMapper.toUserDto(repository.getById(userId));
    }

    @Override
    public void deleteUserById(Long userId) {
        if (!repository.existById(userId)) {
            throw new UserNotFoundException(userId);
        }
        repository.deleteById(userId);

    }

    @Override
    public boolean existUserById(Long userId) {
        return repository.existById(userId);
    }

    private void checkUnicEmail(String email) {
        if (repository.existByEmail(email)) {
            throw new NotUnicEmailException("email " + email + " уже занять другим пользователем");
        }
    }
}