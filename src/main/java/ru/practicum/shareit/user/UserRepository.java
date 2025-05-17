package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;

interface UserRepository extends JpaRepository<User, Long> {

    boolean existById(Long userid);

    boolean existByEmail(String email);
}