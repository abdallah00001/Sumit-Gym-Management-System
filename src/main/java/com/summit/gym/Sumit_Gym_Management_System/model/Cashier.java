package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cashier {

    @Id
    @GeneratedValue
    private Long id;

    private String userName;
    private String password;
    private String phone;


}
