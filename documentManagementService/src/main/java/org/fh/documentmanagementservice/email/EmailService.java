package org.fh.documentmanagementservice.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            // Log the exception for debugging purposes
            System.err.println("Failed to send email to " + to + ": " + e.getMessage());
            return false;
        }
    }

    public boolean sendDocumentNotification(String username, String email, String documentName) {
        String subject = "New Document Version Created";
        String text = String.format("Hello %s, you have received the following document: %s. Please make sure to read it asap.", username, documentName);
        return sendEmail(email, subject, text);
    }
}
