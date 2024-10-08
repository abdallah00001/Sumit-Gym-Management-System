package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
//@ToString(exclude = "subscriptions")
@Builder
public class Shift extends BaseEntity{

    //TODO:: Double check refund related processes

    @Id
    @GeneratedValue
    private Long id;

//    private int num;

    @ManyToOne
    private User user;

    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

//    @OneToMany
//            (cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH})
//    @OneToMany(fetch = FetchType.EAGER)
//    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany

    private List<Payment> payments = new ArrayList<>();

    @OneToMany
    private List<Refund> refunds = new ArrayList<>();

    private int totalRevenue;

    private boolean isActive = true;


    private int calculateTotalRevenue() {
        int subscriptionRevenue = payments.stream()
                .mapToInt(Payment::getFinalPrice)
                .sum();

        int refundedAmount = refunds.stream()
                .mapToInt(Refund::getMoneyRefunded)
                .sum();

        return subscriptionRevenue - refundedAmount;
    }

    public int close() {
        finishDateTime = LocalDateTime.now();
        isActive = false;
        return calculateTotalRevenue();
    }

    @PrePersist
    public void prePersist() {
        startDateTime = LocalDateTime.now();
    }


}


