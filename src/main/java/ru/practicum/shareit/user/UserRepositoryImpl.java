package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;


//@Repository
//public class UserRepositoryImpl implements UserRepository {
//    private static final Map<Long, User> users = new HashMap<>();
//    private static Long currentId = 0L;
//    private static final Set<String> emails = new HashSet<>();
//
//
//    @Override
//    public List<User> findAll() {
//        return users.values().stream().toList();
//    }
//
//    @Override
//    public User save(User user) {
//        user.setId(++currentId);
//        users.put(currentId, user);
//        emails.add(user.getEmail());
//        return user;
//    }
//
//    @Override
//    public User updateUser(User user) {
//        User newUser = users.get(user.getId());
//        if (user.getName() != null) {
//            newUser.setName(user.getName());
//        }
//        if (user.getEmail() != null) {
//            emails.remove(newUser.getEmail());
//            emails.add(user.getEmail());
//            newUser.setEmail(user.getEmail());
//        }
//        return newUser;
//    }
//
//    @Override
//    public void deleteUser(Long userId) {
//        users.remove(userId);
//    }
//
//    @Override
//    public User getUserById(Long userId) {
//        return users.get(userId);
//    }
//
//    @Override
//    public boolean containUserById(Long userid) {
//        return users.containsKey(userid);
//    }
//
//    @Override
//    public boolean checkUnicEmail(String email) {
//        return emails.contains(email);
//    }
//
//
//}
