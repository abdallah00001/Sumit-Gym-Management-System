package com.summit.gym.Sumit_Gym_Management_System.exceptions;

public class InvalidSubscriptionException extends IllegalArgumentException {
    public InvalidSubscriptionException() {
        super("""
                Member has no valid subscriptions
                please check subscriptions are not expired or member is not new
                """);
    }

    public InvalidSubscriptionException(String message) {
        super(message);
    }
}
