package com.appreciateme.notificationservice;

public enum NotificationTemplate {
    NEW_USER {
        public String getText(NotificationData notificationData) {
            return "Welcome to appreciate.me, %s!\n%s %s just created your profile, go to the service and take a look around!"
                    .formatted(notificationData.getReceivingFirstName(), notificationData.getContextFirstName(), notificationData.getContextLastName());
        }

        public String getSubject() {
            return "[appreciate.me] Welcome to appreciate.me!";
        }
    },
    DELETE_USER {
        public String getText(NotificationData notificationData) {
            return "Hi, %s.\nThis is a message from the appreciate.me service to inform you that your profile was deleted by %s %s.\nIf you think this is a mistake, please contact your IT lead."
                    .formatted(notificationData.getReceivingFirstName(), notificationData.getContextFirstName(), notificationData.getContextLastName());
        }

        public String getSubject() {
            return "[appreciate.me] Your account was deleted.";
        }
    },
    BUMP_GOT {
        public String getText(NotificationData notificationData) {
            return "Hi, %s!\nYou just got a new fistbump from %s %s.\nGo to the service to check it out!"
                    .formatted(notificationData.getReceivingFirstName(), notificationData.getContextFirstName(), notificationData.getContextLastName());
        }

        public String getSubject() {
            return "[appreciate.me] You just got a new fistbump!";
        }
    },
    REWARD_READY {
        public String getText(NotificationData notificationData) {
            return "Congratulations, %s!\nWe crunched the numbers and it looks like you can redeem a reward!\nGo to the service to claim it and continue the good work!"
                    .formatted(notificationData.getReceivingFirstName());
        }

        public String getSubject() {
            return "[appreciate.me] A new reward is ready!";
        }
    };

    public abstract String getText(NotificationData notificationData);

    public abstract String getSubject();
}
