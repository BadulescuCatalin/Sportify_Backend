package com.example.mongodb.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfig
{
    @Bean
    public SimpleMailMessage emailTemplate()
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("badulescucatalin01@gmail.com");
        message.setFrom("badulescucatalin01@gmail.com");
        message.setSubject("Ownership Proof");
        message.setText("Ownership Proof");
        return message;
    }
}