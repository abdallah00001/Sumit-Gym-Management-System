package com.summit.gym.Sumit_Gym_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionTypeDto extends BaseDto{

    private Long id;
    private String name;
    private String periodString;
    private Long price;

}
