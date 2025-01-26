package com.myapp.portfoliotracker.controller;


import com.myapp.portfoliotracker.entity.User;
import com.myapp.portfoliotracker.repository.UserRepo;
import com.myapp.portfoliotracker.security.JwtUtil;
import com.myapp.portfoliotracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

   

    /**
     * Login and generate a JWT token.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return A JWT token if authentication is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {

        // Retrieve the user entity from the database
        User authenticatedUser = userRepository.findByEmail(user.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate the JWT token
        String token = jwtUtil.generateToken(authenticatedUser.getEmail());

        // Prepare the response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("userId", authenticatedUser.getId());
        responseBody.put("token", token);

        // Return the response
        return ResponseEntity.ok(responseBody);
    }

    /**
     * Create a new user.
     *
     * @param user The user to be created.
     * @return The created user.
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Create the user
        User createdUser = userService.createUser(user);

        // Generate a JWT token for the created user
        String token = jwtUtil.generateToken(createdUser.getEmail());

        // Return the created user along with the token
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "user", createdUser,
                "token", token
        ));
    }

    /**
     * Get a user by ID.
     *
     * @param userId The ID of the user.
     * @return The user if found.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Get all users.
     *
     * @return A list of all users.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Update an existing user.
     *
     * @param userId The ID of the user to be updated.
     * @param user   The updated user details.
     * @return The updated user.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a user by ID.
     *
     * @param userId The ID of the user to be deleted.
     * @return A response indicating success.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Find a user by email.
     *
     * @param email The email of the user.
     * @return The user if found.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}

