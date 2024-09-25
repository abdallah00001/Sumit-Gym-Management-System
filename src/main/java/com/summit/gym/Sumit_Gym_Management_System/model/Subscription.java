package com.summit.gym.Sumit_Gym_Management_System.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"expireDate", "isExpired", "isFrozen"})
@Builder
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;

//    private int num;

    //    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
//        @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @ManyToOne(cascade = {CascadeType.PERSIST})
//    @ManyToOne
    private Member member;

    @ManyToOne
    private PaymentType paymentType;

    @ManyToOne
    private User user = new User();

    //    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @ManyToOne
    @NotNull(message = "Must specify type")
    private SubscriptionType subscriptionType;

    @ManyToOne
    private Coach privateTrainer;

    private LocalDateTime createdAt;

    private LocalDate startDate;

    private LocalDate expireDate;

    @UniqueElements(message = ValidationUtil.UNIQUE_DATES)
    private List<LocalDate> attendedDays = new ArrayList<>();

    @PositiveOrZero(message = "Discount" + ValidationUtil.POSITIVE)
    private int discount;

    @Positive(message = "Price" + ValidationUtil.POSITIVE)
    private int finalPrice;

    @Transient
    private boolean isExpired;

    @Transient
    private boolean isFrozen;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Freeze> freezeHistory = new ArrayList<>();


    //Allows validation of calculated objects from controller
    @JsonCreator
    public Subscription(
            @JsonProperty("id") Long id,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("startDate") LocalDate startDate,
            @JsonProperty("expireDate") LocalDate expireDate,
            @JsonProperty("attendedDays") List<LocalDate> attendedDays,
            @JsonProperty("subscriptionType") SubscriptionType subscriptionType,
            @JsonProperty("discount") int discount) {

        this.id = id;
        this.subscriptionType = subscriptionType;
        this.discount = discount;
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.startDate = (startDate != null) ? startDate : LocalDate.now();
        this.expireDate = (expireDate != null) ? expireDate : calculateExpireDate();
        this.attendedDays = (attendedDays != null) ? attendedDays : new ArrayList<>();
        this.finalPrice = calculateFinalPrice();
        this.isExpired = isExpired();
    }


    public boolean isExpired() {
        return !expireDate.isAfter(LocalDate.now());
//        return false;
    }

    public boolean isFrozen() {
        Freeze latestFreeze = getLatestFreeze();
        if (latestFreeze == null) return false;
        //The finish date of freeze is normal day with session
        return LocalDate.now().isBefore(latestFreeze.getFinishDate());
    }

    public void freeze(int daysToFreeze) {
        validateBeforeFreeze(daysToFreeze);

        expireDate = expireDate.plusDays(daysToFreeze);
        freezeHistory.add(new Freeze(daysToFreeze));
    }

    private void validateBeforeFreeze(int daysToFreeze) {
        if (isExpired()) {
            throw new IllegalArgumentException("Subscription is expired");
        }

        if (isFrozen()) {
            throw new IllegalArgumentException(
                    "Subscription is already frozen"
            );
        }

        //Checks if the new freeze days will exceed the freeze limit
        int remainingFreezeLimitCount = getRemainingFreezeLimitCount();
        if (remainingFreezeLimitCount < daysToFreeze) {
            throw new IllegalArgumentException(String.format("""
                            Freeze limit exceeded for this subscription
                            Remaining limit: %d,
                            Days provided: %d
                            """,
                    remainingFreezeLimitCount
                    , daysToFreeze));
        }
    }


    //To re activate mid freeze
    public void unFreeze() {
        if (isExpired()) {
            throw new IllegalArgumentException("""
                    Freeze duration already passed,
                    Subscription has expired After the freeze.
                    """);
        }
        if (!isFrozen()) {
            throw new IllegalArgumentException(
                    "Subscription is not frozen"
            );
        }
        Freeze latestFreeze = getLatestFreeze(); //Can't be null since isFrozen is called before

        latestFreeze.setBreakDateTime(LocalDateTime.now());
        latestFreeze.setFinishDate(LocalDate.now());
        int daysFrozen = latestFreeze.getDaysFrozenCount();
        expireDate = calculateExpireDate().plusDays(daysFrozen);

    }

    public int getRemainingFreezeLimitCount() {
        return subscriptionType.getAllowedFreezeDays() - getDaysFrozenCount();
    }

    private int getDaysFrozenCount() {
        return freezeHistory.stream().mapToInt(Freeze::getDaysFrozenCount)
                .sum();
    }

    private Freeze getLatestFreeze() {
        return !freezeHistory.isEmpty() ?
                freezeHistory.getLast() :
                null;
    }

    private int calculateFinalPrice() {
        return privateTrainer == null ?
                subscriptionType.getGeneralPrice() - discount :
                subscriptionType.getPrivateTrainerPrice() - discount;
    }


    private LocalDate calculateExpireDate() {
        return startDate.plusDays(subscriptionType.getDurationInDays());
    }


}


//    @PrePersist
//    @PreUpdate
//    public void setDates() {
//        createdAt = LocalDateTime.now();
//        startDate = LocalDate.now();
//        expireDate = calculateExpireDate();
//        finalPrice = calculateFinalPrice();
////        expireDate = LocalDate.now();
////        expireDate = startDate.plusMonths(1);
//    }