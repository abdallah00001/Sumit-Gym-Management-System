package com.summit.gym.Sumit_Gym_Management_System.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@Pattern(regexp = "^0(10|11|12|15)[0-9]{8}$",
        message = "Please enter a valid phone number" )
public @interface Phone {

    public String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
