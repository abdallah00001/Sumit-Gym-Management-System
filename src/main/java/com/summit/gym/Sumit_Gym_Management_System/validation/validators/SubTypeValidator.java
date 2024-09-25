package com.summit.gym.Sumit_Gym_Management_System.validation.validators;

import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.ValidSubscriptionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;


public class SubTypeValidator extends BaseEntityValidator
        implements ConstraintValidator<ValidSubscriptionType, SubscriptionType> {


    @Override
    public boolean isValid(SubscriptionType subscriptionType, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (subscriptionType.getGeneralPrice() > subscriptionType.getPrivateTrainerPrice()) {
            isValid = false;
            addConstraintViolation(context,
                    "Subscription price with private trainer must be greater than general price",
                    "privateTrainerPrice");
        }

        if (subscriptionType.getDurationInDays() <= subscriptionType.getAllowedFreezeDays()) {
            isValid = false;
            addConstraintViolation(context,
                    "Subscription duration must be longer than allowed freeze days",
                    "allowedFreezeDays");
        }
        return isValid;
    }

    @Override
    public void initialize(ValidSubscriptionType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }



}
