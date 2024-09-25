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
@ToString(exclude = "subscriptions")
@Builder
public class Shift {

    @Id
    @GeneratedValue
    private Long id;

//    private int num;

    @ManyToOne
    private User user;

    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

    @OneToMany
//            (cascade = {CascadeType.PERSIST,CascadeType.MERGE})
//    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH})
//    @OneToMany(fetch = FetchType.EAGER)
    private List<Subscription> subscriptions = new ArrayList<>();

    private int totalMoney;

    private boolean isActive = true;


    private int calculateTotalMoney() {
        return subscriptions.stream()
                .mapToInt(Subscription::getFinalPrice)
                .sum();
    }

    public int close() {
        finishDateTime = LocalDateTime.now();
        isActive = false;
        return calculateTotalMoney();
    }

    @PrePersist
    public void prePersist() {
        startDateTime = LocalDateTime.now();
    }


}
