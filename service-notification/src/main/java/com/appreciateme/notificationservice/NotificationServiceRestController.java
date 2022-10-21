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
    public ResponseEntity<?> sendNotification(@RequestParam(name = "type") NotificationType notificationType, @RequestBody List<String> emailAddresses)
            throws InterruptedException, IOException, URISyntaxException {
        if (notificationType != NotificationType.TEST && notificationType != NotificationType.REWARD_READY && emailAddresses.size() != 2 ) {
            return new ResponseEntity<>("This operation requires 2 email addresses. Received " + emailAddresses.size() + ".", HttpStatus.BAD_REQUEST);
        }
        return notificationType.execute(emailAddresses, usersServiceData.getURI(), javaMailService);
    }
}
