package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Refund {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Subscription subscription;

    private int moneyRefunded;

    private LocalDateTime createdAt;


    @PrePersist
    public void setDates() {
        createdAt = LocalDateTime.now();
    }

}
