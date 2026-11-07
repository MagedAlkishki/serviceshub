package com.magedalaa.serviceshub.config;

import com.magedalaa.serviceshub.model.User;
import com.magedalaa.serviceshub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/** Seeds two demo accounts so the app isn't empty on first run - one already subscribed, one not. */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DataSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        User subscribed = new User("subscribed@example.com", passwordEncoder.encode("password123"));
        subscribed.subscribe();
        userRepository.save(subscribed);

        User free = new User("free@example.com", passwordEncoder.encode("password123"));
        userRepository.save(free);
    }
}
