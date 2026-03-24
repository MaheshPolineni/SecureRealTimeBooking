package com.example.leisuires;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailSenderController {

    @Autowired
    private MailSenderService emailService;

@PostMapping("/send")
public ResponseEntity<String> sendForm(@RequestBody User user) {
    try {
        emailService.sendEmail(user.name, user.email, user.subject, user.message);
        return ResponseEntity.ok("Email sent successfully!");
    } catch (Exception e) {
        return ResponseEntity.ok(e.getMessage());
    }
}
}