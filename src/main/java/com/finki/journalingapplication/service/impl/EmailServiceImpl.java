package com.finki.journalingapplication.service.impl;

import com.finki.journalingapplication.model.User;
import com.finki.journalingapplication.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private final JavaMailSender mailSender;


    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendRegistrationEmail(User user,String subject,String body) {
       // String subject = "Welcome to Our Application";
       // String body = "Dear " + user.getUsername() + ",\n\nWelcome to our application!";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("deardiary.wp.finki@gmail.com");
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

    }
}
