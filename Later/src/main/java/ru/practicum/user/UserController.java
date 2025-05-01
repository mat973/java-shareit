package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.validinterface.Create;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Запрос на получение всех пользователей");
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto saveNewUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.info("Создание нового пользователя {}", userDto);
        return userService.saveUser(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        log.info("Получение пользователя с id {}", userId);
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        log.info("Удаление пользователя с id {}", userId);
        userService.deleteUserById(userId);
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        log.info("Обновление пользователя с id {} полями {}", userId, userDto);
        userDto.setId(userId);
        return userService.updateUser(userDto);

    }

}