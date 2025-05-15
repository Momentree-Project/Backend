package com.momentree.global.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
