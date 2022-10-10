package com.appreciateme.notificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notify")
public class NotificationServiceRestController {

    @Autowired
    JavaMailService javaMailService;

    @PostMapping(value = "/send")
    public ResponseEntity<?> sendNotification(@RequestParam(name = "type") String type, @RequestBody List<String> emailAddresses) {

        switch(type) {
            case "new-user":
                return new ResponseEntity<>("New user notification sent successfully to user " + emailAddresses + ".", HttpStatus.OK);
            case "deleted-user":
                return new ResponseEntity<>("Deleted user notification sent successfully to user " + emailAddresses + ".", HttpStatus.OK);
            case "bump-got":
                return new ResponseEntity<>("New bump notification sent successfully to user " + emailAddresses + ".", HttpStatus.OK);
            case "reward-ready":
                return new ResponseEntity<>("New reward notification sent successfully to user " + emailAddresses + ".", HttpStatus.OK);
            case "test":
                javaMailService.sendTestMessage(emailAddresses.get(0));
                return new ResponseEntity<>("Test email sent.", HttpStatus.OK);
            default:
                return new ResponseEntity<>("Unsupported notification type.", HttpStatus.BAD_REQUEST);
        }
    }
}
