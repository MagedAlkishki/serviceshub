package com.magedalaa.serviceshub.controller;

import com.magedalaa.serviceshub.dto.LoginRequest;
import com.magedalaa.serviceshub.dto.SignupRequest;
import com.magedalaa.serviceshub.dto.UserView;
import com.magedalaa.serviceshub.model.User;
import com.magedalaa.serviceshub.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // The whole session is just one attribute: the logged-in user's id.
    // No Spring Security filter chain - plain servlet HttpSession is enough
    // for a demo with one role and no permissions beyond "subscribed or not".
    private static final String SESSION_USER_ID = "userId";

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserView signup(@Valid @RequestBody SignupRequest request, HttpSession session) {
        User user = userService.signUp(request);
        session.setAttribute(SESSION_USER_ID, user.getId());
        return toView(user);
    }

    @PostMapping("/login")
    public UserView login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        User user = userService.login(request);
        session.setAttribute(SESSION_USER_ID, user.getId());
        return toView(user);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/me")
    public UserView me(HttpSession session) {
        Long userId = currentUserId(session);
        return toView(userService.findById(userId));
    }

    /** Reads the logged-in user's id out of the session, or fails with 401 via ApiExceptionHandler. */
    static Long currentUserId(HttpSession session) {
        Object userId = session.getAttribute(SESSION_USER_ID);
        if (userId == null) {
            throw new NotLoggedInException();
        }
        return (Long) userId;
    }

    private UserView toView(User user) {
        return new UserView(user.getId(), user.getEmail(), user.isSubscribed());
    }

    public static class NotLoggedInException extends RuntimeException {
        public NotLoggedInException() {
            super("You need to log in first");
        }
    }
}
