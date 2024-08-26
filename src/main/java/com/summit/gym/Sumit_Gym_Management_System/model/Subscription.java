package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @ManyToOne
    private PaymentType paymentType;

    @ManyToOne
    private User user;

    @ManyToOne
    private SubscriptionType subscriptionType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Shift shift;

    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate expireDate;
    @Transient
    private boolean isExpired;


    public boolean isExpired() {
        return !expireDate.isAfter(LocalDate.now());
    }

    public int getPrice() {
        return subscriptionType.getPrice();
    }


    @PrePersist
    @PreUpdate
    public void calculateExpireDate() {
//        expireDate = startDate.plusMonths(subscriptionType.getDurationInMonth());
        expireDate = startDate.plusMonths(1);
    }


}
