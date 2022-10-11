package com.appreciateme.notificationservice;

import java.util.NoSuchElementException;

public enum NotificationTemplate {
    NEW_USER, DELETE_USER, BUMP_GOT, REWARD_READY;

    public String getMessage(NotificationData notificationData) {
        switch (this) {
            case BUMP_GOT:
                return "Hi, %s!\nYou just got a new fistbump from %s %s.\nGo to the service to check it out!"
                        .formatted(notificationData.getReceivingFirstName(), notificationData.getContextFirstName(), notificationData.getContextLastName());
            case NEW_USER:
                return "Welcome to appreciate.me, %s!\n%s %s just created your profile, go to the service and take a look around!"
                        .formatted(notificationData.getReceivingFirstName(), notificationData.getContextFirstName(), notificationData.getContextLastName());
            case DELETE_USER:
                return "Hi, %s.\nThis is a message from the appreciate.me service to inform you that your profile was deleted by %s %s.\nIf you think this is a mistake, please contact your IT lead."
                        .formatted(notificationData.getReceivingFirstName(), notificationData.getContextFirstName(), notificationData.getContextLastName());
            case REWARD_READY:
                return "Congratulations, %s!\nWe crunched the numbers and it looks like you can redeem a reward!\nGo to the service to claim it and continue the good work!"
                        .formatted(notificationData.getReceivingFirstName());
            default:
                throw new NoSuchElementException("Not a valid notification type.");
        }

    }

    public String getTopic() {
        switch (this) {
            case REWARD_READY:
                return "[appreciate.me] A new reward is ready!";
            case DELETE_USER:
                return "[appreciate.me] Your account was deleted.";
            case NEW_USER:
                return "[appreciate.me] Welcome to appreciate.me!";
            case BUMP_GOT:
                return "[appreciate.me] You just got a new fistbump!";
            default:
                throw new NoSuchElementException("Not a valid notification type.");

        }
    }
}
