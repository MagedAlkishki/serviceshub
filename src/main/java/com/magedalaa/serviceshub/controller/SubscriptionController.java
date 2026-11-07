package com.magedalaa.serviceshub.controller;

import com.magedalaa.serviceshub.dto.UserView;
import com.magedalaa.serviceshub.model.User;
import com.magedalaa.serviceshub.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Stands in for a real payment step. In a real version this is where a
// payment provider's webhook would confirm payment before flipping
// subscribed to true - no money changes hands here, it's a straight toggle
// so the rest of the platform (the gating logic) can be built and shown
// without needing real billing wired up yet.
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final UserService userService;

    public SubscriptionController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/subscribe")
    public UserView subscribe(HttpSession session) {
        Long userId = AuthController.currentUserId(session);
        User user = userService.subscribe(userId);
        return new UserView(user.getId(), user.getEmail(), user.isSubscribed());
    }
}
