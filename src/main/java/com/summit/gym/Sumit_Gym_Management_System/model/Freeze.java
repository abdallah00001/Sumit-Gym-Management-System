package com.summit.gym.Sumit_Gym_Management_System.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Freeze {

    @Id
    @GeneratedValue
    private Long id;

    private int initialDurationInDays;

    private LocalDate startDate;

    private LocalDate finishDate;

    private LocalDateTime breakDateTime;


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
