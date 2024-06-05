package buisness.services;

import buisness.models.User;
import buisness.models.UserRole;
import datalayer.repositories.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password, UserRole role) {
        // Check if username is already taken
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        User user = new User(username, password, role);
        userRepository.save(user);
        return user;
    }


    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null; // Or throw an exception for invalid credentials
        }
    }
    // ... (other user management logic)
}