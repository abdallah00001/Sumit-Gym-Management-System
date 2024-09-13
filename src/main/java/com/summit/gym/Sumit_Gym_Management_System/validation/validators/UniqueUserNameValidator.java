package com.summit.gym.Sumit_Gym_Management_System.validation.validators;

import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.UniqueUserName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, User> {

    private final UserRepo userRepo;


    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !userRepo.existsByUserName(value.getUsername());
    }

    @Override
    public void initialize(UniqueUserName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }



}
