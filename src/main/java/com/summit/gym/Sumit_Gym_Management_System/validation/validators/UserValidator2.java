package com.summit.gym.Sumit_Gym_Management_System.validation.validators;

import com.summit.gym.Sumit_Gym_Management_System.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserValidator2 {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();


    public Set<String> validate(User user) {
        Set<String> errorMessages = new HashSet<>();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (user.getUsername().contains("cash")) {
            errorMessages.add("it worked");
        }
        if (!violations.isEmpty()) {
            errorMessages.addAll(
                    violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toSet())
            );
        }
        return errorMessages;
    }




}
