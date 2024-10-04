package com.summit.gym.Sumit_Gym_Management_System.exceptions;

import com.summit.gym.Sumit_Gym_Management_System.enums.SubscriptionStatus;

public class InvalidSubscriptionStatusException extends IllegalArgumentException {

    public InvalidSubscriptionStatusException(SubscriptionStatus status) {
        super(generateMessage(status,null));
    }

    public InvalidSubscriptionStatusException(SubscriptionStatus status, String extraMessage) {
        super(generateMessage(status,extraMessage));
    }


    private static String generateMessage(SubscriptionStatus status, String extraMessage) {
        String baseMessage = String.format("Subscription is %s!!, Can't proceed with the operation.",
                status);

        if (extraMessage != null) {
            return String.format("""
                    %s
                    %s
                    """, baseMessage, extraMessage);
        }
        return baseMessage;
    }

}
