package com.summit.gym.Sumit_Gym_Management_System.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShiftDto extends BaseDto{

    private Long id;

    private UserDto userDto;

    private LocalDateTime startDateTime;

    private LocalDateTime finishDateTime;

//    @JsonIgnoreProperties("userDto")
//    private List<SubscriptionDto> subscriptionDtos;

    private int totalMoney;

    private boolean isActive;




}
