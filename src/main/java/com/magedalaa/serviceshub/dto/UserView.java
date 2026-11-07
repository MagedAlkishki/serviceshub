package com.magedalaa.serviceshub.dto;

/** What the frontend is allowed to know about the logged-in user - never the password hash. */
public record UserView(Long id, String email, boolean subscribed) {
}
