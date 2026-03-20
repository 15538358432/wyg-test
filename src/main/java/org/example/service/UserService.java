package org.example.service;

import org.example.entity.User;

import java.util.List;

public interface UserService {

    Long createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();
}
