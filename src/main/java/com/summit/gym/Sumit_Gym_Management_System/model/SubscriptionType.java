package com.summit.gym.Sumit_Gym_Management_System.model;

import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.ValidSubscriptionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ValidSubscriptionType
public class SubscriptionType {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = ValidationUtil.NOT_BLANK)
    private String name;

    @PositiveOrZero(message = "General price" + ValidationUtil.POSITIVE_OR_ZERO)
    private int generalPrice;

    @PositiveOrZero(message = "Price with trainer" + ValidationUtil.POSITIVE_OR_ZERO)
    private int privateTrainerPrice;

    @Min(value = 1,message = "price must be more than zero")
    private int durationInDays;

    @PositiveOrZero(message = "Freeze days" + ValidationUtil.POSITIVE_OR_ZERO)
    private int allowedFreezeDays;



}
