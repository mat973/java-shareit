package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotUnicEmailException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplNegativeTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private final User user = User.builder()
            .id(1L)
            .name("Test")
            .email("test@example.com")
            .build();

    private final UserDto userDto = UserDto.builder()
            .id(1L)
            .name("Test")
            .email("test@example.com")
            .build();

    @Test
    void saveUser_whenEmailAlreadyExists_thenThrowException() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(NotUnicEmailException.class,
                () -> userService.saveUser(userDto));

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_whenUserNotFound_thenThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(userDto));
    }

    @Test
    void updateUser_whenEmailAlreadyExists_thenThrowException() {
        User existing = new User(1L, "Old", "old@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        UserDto updateDto = UserDto.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        assertThrows(NotUnicEmailException.class,
                () -> userService.updateUser(updateDto));
    }

    @Test
    void getUserById_whenUserNotFound_thenThrowException() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(1L));
    }

    @Test
    void deleteUserById_whenUserNotFound_thenThrowException() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUserById(1L));
    }
}

