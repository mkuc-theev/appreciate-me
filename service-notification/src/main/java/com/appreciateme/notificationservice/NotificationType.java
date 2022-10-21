package com.appreciateme.notificationservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public enum NotificationType {
    NEW_USER {
        public ResponseEntity<?> execute(List<String> emails, String userServiceURI, JavaMailService javaMailService)
                throws InterruptedException, IOException, URISyntaxException{
            NotificationData notificationData = new NotificationData(emails.get(0), emails.get(1), userServiceURI);
            javaMailService.sendEmailNotification(notificationData, NotificationTemplate.NEW_USER);
            return new ResponseEntity<>("New user notification sent successfully to user " + emails.get(0) + ".", HttpStatus.OK);
        }
    },
    DELETE_USER {
        public ResponseEntity<?> execute(List<String> emails, String userServiceURI, JavaMailService javaMailService)
                throws InterruptedException, IOException, URISyntaxException{
            NotificationData notificationData = new NotificationData(emails.get(0), emails.get(1), userServiceURI);
            javaMailService.sendEmailNotification(notificationData, NotificationTemplate.DELETE_USER);
            return new ResponseEntity<>("Deleted user notification sent successfully to user " + emails.get(0) + ".", HttpStatus.OK);
        }
    },
    BUMP_GOT {
        public ResponseEntity<?> execute(List<String> emails, String userServiceURI, JavaMailService javaMailService)
                throws InterruptedException, IOException, URISyntaxException{
            NotificationData notificationData = new NotificationData(emails.get(0), emails.get(1), userServiceURI);
            javaMailService.sendEmailNotification(notificationData, NotificationTemplate.BUMP_GOT);
            return new ResponseEntity<>("New bump notification sent successfully to user " + emails.get(0) + ".", HttpStatus.OK);

        }
    },
    REWARD_READY {
        public ResponseEntity<?> execute(List<String> emails, String userServiceURI, JavaMailService javaMailService)
                throws InterruptedException, IOException, URISyntaxException{
            NotificationData notificationData = new NotificationData(emails.get(0), userServiceURI);
            javaMailService.sendEmailNotification(notificationData, NotificationTemplate.REWARD_READY);
            return new ResponseEntity<>("New reward notification sent successfully to user " + emails.get(0) + ".", HttpStatus.OK);
        }
    },
    TEST {
        public ResponseEntity<?> execute(List<String> emails, String userServiceURI, JavaMailService javaMailService)
                throws InterruptedException, IOException, URISyntaxException{
            javaMailService.sendTestMessage(emails.get(0));
            return new ResponseEntity<>("Test email sent to" + emails.get(0) + ".", HttpStatus.OK);
        }
    };
    public abstract ResponseEntity<?> execute(List<String> emails, String userServiceURI, JavaMailService javaMailService)
            throws InterruptedException, IOException, URISyntaxException;
}
