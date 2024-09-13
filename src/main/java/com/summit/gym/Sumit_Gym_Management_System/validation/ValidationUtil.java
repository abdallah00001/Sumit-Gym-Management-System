package com.summit.gym.Sumit_Gym_Management_System.validation;

import java.util.List;

public class ValidationUtil {

    public static final String NOT_BLANK = " can't be empty";
    public static final String UNIQUE_DATES = """
            Date already exists
            Can't add the same date twice to the same subscription
            """;
    public static final String POSITIVE = " must be positive value";

    public static final String UNIQUE_PHONE_CONSTRAINT = "unique_phone";
    public static final String UNIQUE_USERNAME_CONSTRAINT = "unique_username";
    public static final String UNIQUE_NAME_CONSTRAINT = "unique_name";

    public static final List<String> UNIQUE_FIELD_NAMES = List.of(
            "username", "phone", "name"
    );


}
