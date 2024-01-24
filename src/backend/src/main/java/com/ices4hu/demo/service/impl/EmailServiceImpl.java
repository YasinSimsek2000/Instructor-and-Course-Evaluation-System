package com.ices4hu.demo.service.impl;

// Importing required classes

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Term;
import com.ices4hu.demo.enums.Role;
import com.ices4hu.demo.model.EmailDetails;
import com.ices4hu.demo.repository.AppUserRepository;
import com.ices4hu.demo.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.ices4hu.demo.util.ICES4HUUtils.RECIPIENT_EMAIL;

// Annotation
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    final AppUserRepository appUserRepository;
    final TermServiceImpl termService;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private boolean sent = true;

    public String sendSimpleMail(EmailDetails details) {


        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(RECIPIENT_EMAIL); // For testing purposes, emails will only be sen to this account
            //mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }


    public String
    sendMailWithAttachment(EmailDetails details) {

        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {


            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);


            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {

            return "Error while sending mail!!!";
        }
    }

    @Override
    @Scheduled(cron = "1 * 13-18 * * *")
    public void sendMail() {
        if (sent) return;

        System.out.println("\n----------------------------------------\n");
        System.out.println("-------------Sending emails!------------");
        System.out.println("\n----------------------------------------\n");
        Term currentTerm = termService.getCurrentTerm();

        String reminderMessage
                = "Your term will start at: "
                + currentTerm.getStartDate()
                + "\nAnd will end at: "
                + currentTerm.getEndDate();

        var reminderEmail = EmailDetails.builder()
                .recipient(RECIPIENT_EMAIL)
                .msgBody(reminderMessage)
                .subject("Reminder For Surveys!")
                .build();

        sendMailToAllStudents(reminderEmail);

        sent = true;
    }

    @Override
    public void sendMailToAllStudents(EmailDetails emailDetails) {
        List<AppUser> students = appUserRepository
                .findAll()
                .stream()
                .filter(appUser -> appUser.getRole() == Role.STUDENT)
                .toList();

        for (var student:students) {
            emailDetails.setRecipient(student.getEmail());
            emailDetails.setMsgBody(emailDetails.getMsgBody() + "\n\nUsername: " + student.getUsername() + "\n");
            sendSimpleMail(emailDetails);
            if (student.getSecondEmail() != null) {
                emailDetails.setRecipient(student.getSecondEmail());
                sendSimpleMail(emailDetails);
            }
        }
    }

}
