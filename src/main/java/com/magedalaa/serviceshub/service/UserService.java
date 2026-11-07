package com.magedalaa.serviceshub.service;

import com.magedalaa.serviceshub.dto.LoginRequest;
import com.magedalaa.serviceshub.dto.SignupRequest;
import com.magedalaa.serviceshub.model.User;
import com.magedalaa.serviceshub.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("An account with this email already exists");
        }
        User user = new User(request.email(), passwordEncoder.encode(request.password()));
        return userRepository.save(user);
    }

    // BCrypt hashes are salted, so the same password hashes differently each
    // time - matches() re-derives the hash with the stored salt to compare,
    // rather than comparing hash strings directly.
    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NoSuchElementException("Invalid email or password"));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new NoSuchElementException("Invalid email or password");
        }
        return user;
    }

    public User subscribe(Long userId) {
        User user = findById(userId);
        user.subscribe();
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("No user with id " + userId));
    }
}
