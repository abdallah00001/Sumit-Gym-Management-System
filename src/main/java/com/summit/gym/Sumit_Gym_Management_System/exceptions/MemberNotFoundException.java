package com.summit.gym.Sumit_Gym_Management_System.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
    public MemberNotFoundException() {
        super("Member was not found");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
