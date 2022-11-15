package org.ethiopiancloud.crms.services;

import org.ethiopiancloud.crms.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();
    Optional<User> getUserById(Integer id);
    User addNewUser(User newUser);
    User updateUserById(Integer userId, User updateUser);
    void deleteUser(Integer deleteId);
}
