package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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


    public int calculateTotalMoney() {
        return subscriptions.stream()
                .mapToInt(Subscription::getFinalPrice)
                .sum();
    }

    public void close() {
        finishDateTime = LocalDateTime.now();
        isActive = false;
    }

    @PrePersist
    public void prePersist() {
        startDateTime = LocalDateTime.now();
    }


}
