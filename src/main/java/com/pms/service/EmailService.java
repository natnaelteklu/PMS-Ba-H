package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendEmail(String toAddress, String fromAddress, String subject, String message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(toAddress);
            mailMessage.setFrom(fromAddress);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendHtmlEmail(String toAddress, String fromAddress, String senderName, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toAddress);
            helper.setFrom(fromAddress, senderName);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Enable HTML content
            javaMailSender.send(mimeMessage);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
