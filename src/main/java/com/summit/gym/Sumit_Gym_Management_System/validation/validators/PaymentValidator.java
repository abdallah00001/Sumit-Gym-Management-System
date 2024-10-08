package com.summit.gym.Sumit_Gym_Management_System.validation.validators;

import com.summit.gym.Sumit_Gym_Management_System.model.Payment;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.ValidPayment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PaymentValidator extends BaseEntityValidator
        implements ConstraintValidator<ValidPayment, Payment> {


    @Override
    public boolean isValid(Payment payment, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (payment.getOriginalPrice() < payment.getDiscount()) {
            isValid = false;
            addConstraintViolation(context,"Discount can't be greater than subscription price",
                    "discount");
        }

        return isValid;
    }



}
