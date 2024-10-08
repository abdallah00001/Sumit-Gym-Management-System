package com.summit.gym.Sumit_Gym_Management_System.dto.request_dtos;


import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionSaveRequestDto {

    private Member member;
    private Payment payment;

}
