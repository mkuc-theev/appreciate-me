package com.appreciateme.notificationservice;

public class NotificationData {
    private String receivingFirstName;
    private String receivingLastName;
    private String receivingEmail;
    private String contextFirstName = null;
    private String contextLastName = null;

    NotificationData(String receivingEmail, String contextEmail) {
        //HTTP request
    }

    NotificationData(String receivingEmail) {
        //HTTP request
    }

    public String getContextFirstName() {
        return contextFirstName;
    }

    public String getContextLastName() {
        return contextLastName;
    }

    public String getReceivingEmail() {
        return receivingEmail;
    }

    public String getReceivingFirstName() {
        return receivingFirstName;
    }

    public String getReceivingLastName() {
        return receivingLastName;
    }
}
