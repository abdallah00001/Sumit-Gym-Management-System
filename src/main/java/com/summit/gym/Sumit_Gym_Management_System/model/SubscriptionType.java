package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubscriptionType {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private int durationInMonth;

}
