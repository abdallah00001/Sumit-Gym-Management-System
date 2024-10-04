package com.summit.gym.Sumit_Gym_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubscriptionForMemberDto extends BaseDto{

    private Long id;
    private SubscriptionTypeSummaryDto typeSummaryDto;
    private LocalDate startDate;
    private LocalDate expireDate;
    private String passedPeriodString;
    private String remainingPeriodString;
    private int sessionsAttended;

}
