//package com.summit.gym.Sumit_Gym_Management_System.validation.validators;
//
//import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
///*
//* Not used
//* */
//@Component
//@RequiredArgsConstructor
//public class SubscriptionTypeValidator implements Validator {
//
//    private static final String INVALID_CODE = "invalid";
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return SubscriptionType.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        SubscriptionType subscriptionType = (SubscriptionType) target;
//
//        if (subscriptionType.getPeriod().getDays() <= subscriptionType.getAllowedFreezeDays()) {
//            errors.rejectValue("allowedFreezeDays", INVALID_CODE,
//                    "Allowed freeze days must be smaller than subscription duration");
//        }
//
//    }
//
//    //Add BindingResult result to controller method params
///*    validator.validate(type,result);
//        if (result.hasErrors()) {
//            System.out.println(result);
//            Method save = SubscriptionTypeController.class.getMethod("save", SubscriptionType.class, BindingResult.class);
//            throw new MethodArgumentNotValidException(new MethodParameter(save, 0), result);
//        }*/
//}
