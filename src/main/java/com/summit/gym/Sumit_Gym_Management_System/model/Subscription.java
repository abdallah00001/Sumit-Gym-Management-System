package com.summit.gym.Sumit_Gym_Management_System.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.summit.gym.Sumit_Gym_Management_System.enums.PaymentType;
import com.summit.gym.Sumit_Gym_Management_System.enums.SubscriptionStatus;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.InvalidSubscriptionStatusException;
import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.summit.gym.Sumit_Gym_Management_System.enums.SubscriptionStatus.ACTIVE;
import static com.summit.gym.Sumit_Gym_Management_System.enums.SubscriptionStatus.FROZEN;

@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"expireDate"})
@Builder
public class Subscription extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Member" + ValidationUtil.NOT_NULL)
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Member member;

//    @NotNull(message = "Payment type" + ValidationUtil.NOT_NULL)
//    @Enumerated(value = EnumType.STRING)
//    private PaymentType paymentType;

    @ManyToOne
    private User user;

    @ManyToOne
//    @NotNull(message = "Subscription type" + ValidationUtil.NOT_NULL)
    private SubscriptionType subscriptionType;

    @ManyToOne
    private Coach privateTrainer;

    private LocalDateTime createdAt;

    private LocalDate startDate;

    private LocalDate expireDate;

    @ElementCollection
    @UniqueElements(message = ValidationUtil.UNIQUE_DATES)
    private List<LocalDate> attendedDays = new ArrayList<>();

//    @PositiveOrZero(message = "Discount" + ValidationUtil.POSITIVE)
//    private int discount;

//    @Positive(message = "Price" + ValidationUtil.POSITIVE)
//    @PositiveOrZero(message = "Price" + ValidationUtil.POSITIVE_OR_ZERO)
//    private int finalPrice;

    @JsonIgnore
    private boolean isCancelled;

    @Transient
    private SubscriptionStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Freeze> freezeHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Payment> payments = new ArrayList<>();


    private String notes;


    //Calculated fields are now automatically populated when received in controller
    //Allows validation of calculated objects from controller
/*    @JsonCreator
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
        this.attendedDays = (attendedDays != null) ? attendedDays : new ArrayList<>();
        this.expireDate = (expireDate != null) ? expireDate : calculateExpireDate();
        this.finalPrice = calculateFinalPrice();
        this.status = getStatus();
    }*/


    //Frozen and Expired need to be calculated while cancelled is persisted
    public SubscriptionStatus getStatus() {
        SubscriptionStatus status = ACTIVE;
        if (isCancelled) {
            status = SubscriptionStatus.CANCELLED;
        } else if (isExpired()) {
            status = SubscriptionStatus.EXPIRED;
        } else if (isFrozen()) {
            status = SubscriptionStatus.FROZEN;
        }
        return status;
    }

    public Payment getLatestPayment() {
        return payments.getLast();
    }

    private boolean isExpired() {
        return !expireDate.isAfter(LocalDate.now());
    }

    private boolean isFrozen() {
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
        SubscriptionStatus status = getStatus();

        if (!status.equals(ACTIVE)) {
            throw new InvalidSubscriptionStatusException(status,
                    "Only ACTIVE subscriptions can be frozen");
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
        SubscriptionStatus status = getStatus();
        if (!status.equals(FROZEN)) {
            throw new InvalidSubscriptionStatusException(status,
                    "Couldn't unfreeze subscription because it is not frozen"
            );
        }

        //Can't be null since isFrozen is called before with null check included
        Freeze latestFreeze = getLatestFreeze();

        latestFreeze.setBreakDateTime(LocalDateTime.now());
        latestFreeze.setFinishDate(LocalDate.now());
        int daysFrozen = latestFreeze.getDaysFrozenCount();
//        expireDate = calculateExpireDate().plusDays(daysFrozen);
        //Ex: if initial freeze days were 20, and we broke it after only 5
        //Subtract 15 days from expire date
        expireDate = expireDate.minusDays((latestFreeze.getInitialDurationInDays() - daysFrozen));
    }

    //Use of Period to account for days per month variation
    public Period getRemainingPeriod() {
        return Period.between(LocalDate.now(), expireDate);
    }

    public Period getpassedPeriod() {
        return Period.between(startDate, LocalDate.now());
    }

    public int getAttendedDaysCount() {
        return attendedDays.size();
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

//    private int calculateFinalPrice() {
//        return subscriptionType.getPrice() - discount;
//    }


    private LocalDate calculateExpireDate() {
        return startDate.plus(subscriptionType.getPeriod());
    }



    @PrePersist
//    @PreUpdate
    public void setDates() {
        startDate = LocalDate.now();
        createdAt = LocalDateTime.now();
        expireDate = calculateExpireDate();
//        finalPrice = calculateFinalPrice();


    }
}


