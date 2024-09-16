package com.summit.gym.Sumit_Gym_Management_System.exceptions;

public class ActiveShiftReassignedException extends RuntimeException{

    private static final String MESSAGE =
            "User already has an active shift. Reassigned existing shift.";

    public ActiveShiftReassignedException() {
        super(MESSAGE);
    }
}
