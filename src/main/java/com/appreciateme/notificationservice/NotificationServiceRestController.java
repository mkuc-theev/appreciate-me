package com.appreciateme.notificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/notify")
public class NotificationServiceRestController {

    @Autowired
    JavaMailService javaMailService;

    @Autowired
    UsersServiceData usersServiceData;

    @PostMapping(value = "/send")
    public ResponseEntity<?> sendNotification(@RequestParam(name = "type") String type, @RequestBody List<String> emailAddresses)
            throws InterruptedException, IOException, URISyntaxException {

        switch (type) {
            case "new-user":
                if (emailAddresses.size() != 2) {
                    return new ResponseEntity<>("This operation requires 2 email addresses. Received " + emailAddresses.size(), HttpStatus.BAD_REQUEST);
                }
                javaMailService.sendEmailNotification(new NotificationData(emailAddresses.get(0), emailAddresses.get(1), usersServiceData.getURI()), NotificationTemplate.NEW_USER);
                return new ResponseEntity<>("New user notification sent successfully to user " + emailAddresses.get(0) + ".", HttpStatus.OK);
            case "deleted-user":
                if (emailAddresses.size() != 2) {
                    return new ResponseEntity<>("This operation requires 2 email addresses. Received " + emailAddresses.size(), HttpStatus.BAD_REQUEST);
                }
                javaMailService.sendEmailNotification(new NotificationData(emailAddresses.get(0), emailAddresses.get(1), usersServiceData.getURI()), NotificationTemplate.DELETE_USER);
                return new ResponseEntity<>("Deleted user notification sent successfully to user " + emailAddresses.get(0) + ".", HttpStatus.OK);
            case "bump-got":
                if (emailAddresses.size() != 2) {
                    return new ResponseEntity<>("This operation requires 2 email addresses. Received " + emailAddresses.size(), HttpStatus.BAD_REQUEST);
                }
                javaMailService.sendEmailNotification(new NotificationData(emailAddresses.get(0), emailAddresses.get(1), usersServiceData.getURI()), NotificationTemplate.BUMP_GOT);
                return new ResponseEntity<>("New bump notification sent successfully to user " + emailAddresses.get(0) + ".", HttpStatus.OK);
            case "reward-ready":
                javaMailService.sendEmailNotification(new NotificationData(emailAddresses.get(0), usersServiceData.getURI()), NotificationTemplate.REWARD_READY);
                return new ResponseEntity<>("New reward notification sent successfully to user " + emailAddresses.get(0) + ".", HttpStatus.OK);
            case "test":
                javaMailService.sendTestMessage(emailAddresses.get(0));
                return new ResponseEntity<>("Test email sent.", HttpStatus.OK);
            default:
                return new ResponseEntity<>("Unsupported notification type, please check request parameters.", HttpStatus.BAD_REQUEST);
        }
    }
}
