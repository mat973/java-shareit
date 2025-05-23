package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(Long userid);

    boolean existsByEmail(String email);
}