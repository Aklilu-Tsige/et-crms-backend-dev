package org.ethiopiancloud.crms.controllers;

import org.ethiopiancloud.crms.models.User;
import org.ethiopiancloud.crms.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("api/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(
                        user -> {
                            try {
                                return ResponseEntity
                                        .ok()
                                        .eTag(user.getUserName())
                                        .location(new URI("/users" + user.getId()))
                                        .body(user);
                            } catch (URISyntaxException e) {
                                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                            }
                        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * @param user
     * @return
     */
    @PostMapping("/users/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addNewUser(user);
        try {
            return ResponseEntity
                    .created(new URI("/users" + newUser.getUserName()))
                    .eTag(newUser.getUserName())
                    .body(newUser);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     *
     * @param userId
     * @param updateUser
     * @return
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User updateUser) {

        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isEmpty())
            return ResponseEntity.notFound().build();

        updateUser.setId(userId);
        userService.addNewUser(updateUser);
        return ResponseEntity.noContent().build();
    }

    /**
     * 
     * @param userId
     */
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
