    package com.example.springsecurityjwt.Service;

    import com.example.springsecurityjwt.Repository.UserRepository;
    import com.example.springsecurityjwt.entities.User;
    import com.example.springsecurityjwt.exceptions.ResourceNotFoundException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.util.StringUtils;

    import java.util.List;

    @Service
    public class UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        // Update user details (username and email)
        public User updateUser(Long userId, User.UserUpdateRequest request) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

            if (StringUtils.hasText(request.getUsername())) {
                user.setUsername(request.getUsername());
            }

            // Fix: Update email correctly
            if (StringUtils.hasText(request.getEmail())) {
                user.setEmail(request.getEmail());
            }

            return userRepository.save(user);
        }

        // Update password for the user
        public void updatePassword(Long userId, User.PasswordUpdateRequest request) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new BadCredentialsException("Current password is incorrect");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        }

        // Save updated user (for any modifications)
        public User saveUpdatedUser(User user) {
            return userRepository.save(user);
        }
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        public User getUserById(Long id) {
            return userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        }

        public User createUser(User user) {
            return userRepository.save(user);
        }

        public User updateUser(Long id, User userDetails) {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            // Update other fields as necessary
            return userRepository.save(existingUser);
        }

        public void deleteUser(Long id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            userRepository.delete(user);
        }
    }
