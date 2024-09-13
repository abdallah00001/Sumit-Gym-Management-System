package com.summit.gym.Sumit_Gym_Management_System.validation.annotations;

import com.summit.gym.Sumit_Gym_Management_System.validation.validators.UniqueUserNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UniqueUserNameValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserName {
    String message() default "ANNOTATION:  User name already Exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
