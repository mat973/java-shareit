package ru.practicum.shareit.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserServiceImplIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser_shouldSaveAndReturnUser() {
        UserDto userDto = UserDto.builder().name("John").email("john@example.com").build();
        UserDto saved = userService.saveUser(userDto);

        assertNotNull(saved.getId());
        assertEquals("John", saved.getName());
        assertEquals("john@example.com", saved.getEmail());
    }

    @Test
    void updateUser_shouldUpdateNameAndEmail() {
        User user = userRepository.save(User.builder().name("Old").email("old@mail.com").build());

        UserDto updated = UserDto.builder()
                .id(user.getId())
                .name("New")
                .email("new@mail.com")
                .build();

        UserDto result = userService.updateUser(updated);

        assertEquals("New", result.getName());
        assertEquals("new@mail.com", result.getEmail());
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = userRepository.save(User.builder().name("Alex").email("alex@mail.com").build());
        UserDto found = userService.getUserById(user.getId());

        assertEquals(user.getName(), found.getName());
        assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    void getAllUsers_shouldReturnAll() {
        userRepository.save(User.builder().name("A").email("a@mail.com").build());
        userRepository.save(User.builder().name("B").email("b@mail.com").build());

        List<UserDto> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void deleteUserById_shouldDeleteUser() {
        User user = userRepository.save(User.builder().name("ToDel").email("del@mail.com").build());

        userService.deleteUserById(user.getId());

        assertFalse(userRepository.existsById(user.getId()));
    }
}
