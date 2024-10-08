package com.summit.gym.Sumit_Gym_Management_System.validation.annotations;


import com.summit.gym.Sumit_Gym_Management_System.validation.validators.PaymentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PaymentValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPayment {
    String message() default "Invalid payment";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
