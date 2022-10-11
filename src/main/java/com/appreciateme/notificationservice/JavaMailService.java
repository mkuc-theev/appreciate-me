package com.appreciateme.notificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaMailService {

    private final String myEmail = "app.appreciate.me@gmail.com";
    @Autowired
    JavaMailSender emailSender;

    public void sendTestMessage(String address) {
        SimpleMailMessage testMessage = new SimpleMailMessage();

        testMessage.setFrom("app.appreciate.me@gmail.com");
        testMessage.setTo(address);
        testMessage.setSubject("Test");
        testMessage.setText("This is a test email from the appreciate.me app, please ignore.");

        emailSender.send(testMessage);
    }

    public void sendEmailNotification(NotificationData notificationData, NotificationTemplate notificationTemplate) {
        SimpleMailMessage notification = new SimpleMailMessage();

        notification.setFrom(myEmail);
        notification.setTo(notificationData.getReceivingEmail());
        notification.setSubject(notificationTemplate.getTopic());
        notification.setText(notificationTemplate.getMessage(notificationData));

        emailSender.send(notification);
    }
}
