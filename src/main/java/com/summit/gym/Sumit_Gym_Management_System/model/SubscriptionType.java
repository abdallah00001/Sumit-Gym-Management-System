package com.summit.gym.Sumit_Gym_Management_System.model;

import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubscriptionType {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = ValidationUtil.NOT_BLANK)
    private String name;

    @Min(value = 0,message = "price can't be lower than zero")
    private int price;

    @Min(value = 1,message = "price must be more than zero")
    private int durationInDays;

}
