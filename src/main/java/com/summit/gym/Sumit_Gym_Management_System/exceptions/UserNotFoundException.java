package com.summit.gym.Sumit_Gym_Management_System.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    private final static String DEFAULT_MESSAGE =
            "User was not found";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(String customMsg) {
        super(customMsg);
    }
}
