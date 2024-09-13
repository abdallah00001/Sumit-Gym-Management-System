package com.summit.gym.Sumit_Gym_Management_System.dto;

import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.PaymentType;
import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionDto {

    private Long id;
    private Member member;
    private PaymentType paymentType;
    private UserDto userDto;
    private SubscriptionType subscriptionType;
    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate expireDate;
    private List<LocalDate> attendedDays = new ArrayList<>();
    private boolean isExpired;
    private int discount;
    private int finalPrice;


}
