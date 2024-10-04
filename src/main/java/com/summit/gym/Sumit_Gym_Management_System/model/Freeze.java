package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Freeze extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private int initialDurationInDays;

    private LocalDate startDate;

    private LocalDate finishDate;

    private LocalDateTime breakDateTime;


    //Create a freeze with both start and finish date known
    //finishDate can change if freeze was broken mid-freeze
    public Freeze(int initialDurationInDays) {
        this.initialDurationInDays = initialDurationInDays;
        startDate = LocalDate.now();
        finishDate = startDate.plusDays(initialDurationInDays);
    }

    public int getDaysFrozenCount() {
        return (int) ChronoUnit.DAYS.between(startDate, finishDate);
    }

    public List<LocalDate> getDatesFrozen() {
        List<LocalDate> frozenDates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(finishDate)) {
            frozenDates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return frozenDates;
    }


}
