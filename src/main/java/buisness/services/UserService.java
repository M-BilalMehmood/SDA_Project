package buisness.services;

import buisness.models.User;
import buisness.models.UserRole;
import datalayer.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

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

    public void registerUser(User user) {
        String hashedPassword = hashPassword(user.getPassword()); // Implement hashPassword() method
        user.setPassword(hashedPassword);

        // 2. Save the user to the database
        userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        // 1. Retrieve user from the database
        User user = userRepository.findByUsername(username);

        // 2. Verify password (use the same hashing algorithm as in registration)
        if (user != null && verifyPassword(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    // In UserService.java
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}