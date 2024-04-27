package com.cashrich.CashRichAssessment.Service;

import com.cashrich.CashRichAssessment.DTO.UpdateUserRequest;
import com.cashrich.CashRichAssessment.DTO.UserDTO;
import com.cashrich.CashRichAssessment.Entity.User;
import com.cashrich.CashRichAssessment.Exception.CashRichException;
import com.cashrich.CashRichAssessment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        if (null != userRepository.findByUsername(userDTO.getUsername()) || null != userRepository.findByEmail(userDTO.getEmail())){
            throw new CashRichException("User Already Exists");
        }
        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setMobile(userDTO.getMobile());

        User updatedUser = userRepository.save(existingUser);

        return convertToDTO(updatedUser);
    }

    public UserDTO updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setFirstName(updateUserRequest.getFirstName());
        existingUser.setLastName(updateUserRequest.getLastName());
        existingUser.setMobile(updateUserRequest.getMobile());

        User updatedUser = userRepository.save(existingUser);

        return convertToDTO(updatedUser);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobile(user.getMobile());
        return userDTO;
    }

    public UserDTO getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return convertToDTO(optionalUser.get());
        } else {
            // Handle case when user is not found
            throw new CashRichException("User not found with ID: " + userId);
        }
    }

    public UserDTO getByUserName(String username) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        if (optionalUser.isPresent()) {
            return convertToDTO(optionalUser.get());
        } else {
            // Handle case when user is not found
            throw new CashRichException("User not found with ID: " + username);
        }
    }

}
