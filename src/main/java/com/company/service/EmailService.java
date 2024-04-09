package com.company.service;

import com.company.repository.IEmployeeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService implements IEmailService{

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Scheduled(cron = "0 12 12 * * ?")
    public void sendEmails() throws MessagingException {
       List<String> recipients = employeeRepository.getAllEmails();
       String subject = "Noon Email";
       String content = "<h1>Hello!</h1><p>This is a scheduled email sent at noon.</p>";
        for (String recipient : recipients) {
            sendEmail(recipient, subject, content);
        }
    }

    @Override
    public void sendEmail(String recipient, String subject, String content) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(content,true);
        emailSender.send(message);
    }
}
