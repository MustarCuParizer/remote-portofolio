package com.learning.quiz_api.impl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendVerificationEmail(String toEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("n.antonel2008@gmail.com");

        var verificationLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/public/v1/user/verify-email/{email}/{code}")
                .buildAndExpand(toEmail, verificationCode)
                .toUriString();

        message.setTo(toEmail);
        message.setSubject("Your Verification Code");
        message.setText("Your verification link is: " + verificationLink + "\n\nThis code will expire in 10 minutes.");
        mailSender.send(message);
    }
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}