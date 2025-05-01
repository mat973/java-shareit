package ru.practicum.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Map<Long, User> users = new HashMap<>();
    private static Long currentId = 0L;


    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User save(User user) {
        user.setId(++currentId);
        users.put(currentId, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        User newUser = users.get(user.getId());
        if (user.getName() != null) {
            newUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            newUser.setEmail(user.getEmail());
        }
        return newUser;
    }

    @Override
    public void deleteUser(Long userId) {
        users.remove(userId);
    }

    @Override
    public User getUserById(Long userId) {
        return users.get(userId);
    }

    @Override
    public boolean containUserById(Long userid) {
        return users.containsKey(userid);
    }
}
