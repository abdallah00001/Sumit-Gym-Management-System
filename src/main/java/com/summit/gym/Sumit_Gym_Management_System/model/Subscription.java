package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Member member;
    private PaymentType paymentType;
    private Coach coach;
    private Cashier cashier;
    private SubscriptionType subscriptionType;
    private LocalDate startDate;
    private LocalDate expireDate;

    public boolean isExpired() {
        return expireDate.isAfter(LocalDate.now());
    }

//    @PrePersist
//    @PreUpdate
//    public void calculateExpireDate() {
//        expireDate = startDate.plusMonths(subscriptionType.getDurationInMonth());
//    }



}
