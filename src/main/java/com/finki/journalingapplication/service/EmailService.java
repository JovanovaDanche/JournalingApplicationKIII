package com.finki.journalingapplication.service;

import com.finki.journalingapplication.model.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    @Async
    void sendRegistrationEmail(User user,String subject,String body);
}
