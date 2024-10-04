package com.summit.gym.Sumit_Gym_Management_System.dto;

import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionTypeSummaryDto extends BaseDto{

    private Long id;
    private String name;
//    private String periodString;
}
