package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @ManyToOne
    private User user;

    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Subscription> subscriptions;

    private int totalMoney;

    public int calculateTotalMoney() {
        return subscriptions.stream()
                .mapToInt(Subscription::getPrice)
                .sum();
    }




}
