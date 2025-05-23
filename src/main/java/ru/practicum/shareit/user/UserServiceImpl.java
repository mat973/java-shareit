package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.practicum.shareit.exception.NotUnicEmailException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

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
        User updateUser = repository.findById(userDto.getId()).orElseThrow(() ->
                new UserNotFoundException(userDto.getId()));
        if (userDto.getName() != null){
            updateUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            checkUnicEmail(userDto.getEmail());
            updateUser.setEmail(userDto.getEmail());
        }
        return UserMapper.toUserDto(repository.save(updateUser));
    }

    @Override
    public UserDto getUserById(Long userId) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return UserMapper.toUserDto(repository.getById(userId));
    }

    @Override
    public void deleteUserById(Long userId) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        repository.deleteById(userId);

    }

    @Override
    public boolean existUserById(Long userId) {
        return repository.existsById(userId);
    }

    @Override
    public Optional<User> getClearUser(Long userId) {
        return repository.findById(userId);
    }

    private void checkUnicEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new NotUnicEmailException("email " + email + " уже занять другим пользователем");
        }
    }
}