package com.company.service;

import jakarta.mail.MessagingException;

import java.util.List;

public interface IEmailService {
    void sendEmails() throws MessagingException;

    void sendEmail(String recipient, String subject, String content) throws MessagingException;
}
