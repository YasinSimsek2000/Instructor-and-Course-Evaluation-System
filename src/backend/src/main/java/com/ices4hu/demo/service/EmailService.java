package com.ices4hu.demo.service;

import com.ices4hu.demo.model.EmailDetails;
import org.springframework.scheduling.annotation.Scheduled;

// Interface
public interface EmailService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

    @Scheduled(cron = "1 * 13-18 * * *")
    void sendMail();

    void sendMailToAllStudents(EmailDetails emailDetails);
}