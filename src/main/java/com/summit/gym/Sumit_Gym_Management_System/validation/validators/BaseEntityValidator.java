package com.summit.gym.Sumit_Gym_Management_System.validation.validators;


import jakarta.validation.ConstraintValidatorContext;

public abstract class BaseEntityValidator {

    protected void addConstraintViolation(ConstraintValidatorContext context, String message, String propertyName){
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propertyName)
                .addConstraintViolation();
    }




}
