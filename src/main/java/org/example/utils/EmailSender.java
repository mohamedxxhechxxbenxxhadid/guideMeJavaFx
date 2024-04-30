package org.example.utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailSender {
    public static void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        // Set mail properties for Mailtrap
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        properties.put("mail.smtp.port", "2525");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS
        properties.put("mail.smtp.ssl.trust", "sandbox.smtp.mailtrap.io"); // Trust Mailtrap's SSL certificate

        // Provide Mailtrap username and password
        final String username = "669cb1d705e432";
        final String password = "81abd9b332ad6e";

        // Create session with Authenticator
        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Create message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setText(body);

        // Send message
        Transport.send(message);
    }
}