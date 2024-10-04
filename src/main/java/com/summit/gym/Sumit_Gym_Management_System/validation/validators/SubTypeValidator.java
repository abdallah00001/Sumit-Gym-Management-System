package com.summit.gym.Sumit_Gym_Management_System.validation.validators;

import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.ValidSubscriptionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class SubTypeValidator extends BaseEntityValidator
        implements ConstraintValidator<ValidSubscriptionType, SubscriptionType> {


    @Override
    public boolean isValid(SubscriptionType subscriptionType, ConstraintValidatorContext context) {
        boolean isValid = true;

        //Can't practically compare 2 periods because days variations between months
        //So we compare the total days
        long subscriptionLengthInDays = ChronoUnit.DAYS
                .between(LocalDate.now(), LocalDate.now().plus(subscriptionType.getPeriod()));

        if (subscriptionLengthInDays < subscriptionType.getAllowedFreezeDays()) {

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
