package com.magedalaa.serviceshub.controller;

import com.magedalaa.serviceshub.service.QrCodeService;
import com.magedalaa.serviceshub.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/services/qr")
public class QrCodeController {

    private final QrCodeService qrCodeService;
    private final UserService userService;

    public QrCodeController(QrCodeService qrCodeService, UserService userService) {
        this.qrCodeService = qrCodeService;
        this.userService = userService;
    }

    // This is the gate: every other service added to the platform later
    // follows the same shape - require login, then require subscribed,
    // only then do the actual work.
    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generate(@RequestParam String text, HttpSession session) {
        Long userId = AuthController.currentUserId(session);
        if (!userService.findById(userId).isSubscribed()) {
            throw new IllegalStateException("Subscribe to use the QR Code Generator");
        }
        return qrCodeService.generate(text);
    }
}
