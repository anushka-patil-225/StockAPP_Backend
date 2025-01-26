package com.myapp.portfoliotracker.service;

import com.myapp.portfoliotracker.entity.User;
import com.myapp.portfoliotracker.exceptions.ResourceNotFoundException;
import com.myapp.portfoliotracker.exceptions.BadRequestException;
import com.myapp.portfoliotracker.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;
    
//    @Autowired(required=true)
//    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Create a new user.
     *
     * @param user The user to be created.
     * @return The created user.
     */
    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User with email " + user.getEmail() + " already exists.");
        }
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
        
        return userRepository.save(user);
    }
    
    

    /**
     * Get a user by ID.
     *
     * @param userId The ID of the user.
     * @return The user if found.
     */
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));
    }

    /**
     * Get all users.
     *
     * @return A list of all users.
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Update an existing user.
     *
     * @param userId The ID of the user to be updated.
     * @param updatedUser The updated user details.
     * @return The updated user.
     */
    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        if (!existingUser.getEmail().equals(updatedUser.getEmail()) && userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new BadRequestException("Email " + updatedUser.getEmail() + " is already in use.");
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        return userRepository.save(existingUser);
    }

    /**
     * Delete a user by ID.
     *
     * @param userId The ID of the user to be deleted.
     */
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }
        userRepository.deleteById(userId);
    }

    /**
     * Find a user by email.
     *
     * @param email The email of the user.
     * @return The user if found.
     */
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));
    }

    /**
     * Load a user by username (email) for Spring Security.
     *
     * @param email The email of the user.
     * @return A UserDetails object.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
