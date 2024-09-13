package com.summit.gym.Sumit_Gym_Management_System.model;

import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import com.summit.gym.Sumit_Gym_Management_System.validation.groups.OnSave;
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
@ToString(exclude = {"expireDate", "isExpired"})
@Builder
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;

//    private int num;

    //    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @ManyToOne
    private Member member;

    @ManyToOne
    private PaymentType paymentType;

    @ManyToOne
    private User user = new User();

    //    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @ManyToOne
    @NotNull(message = "Must specify type")
    private SubscriptionType subscriptionType;

    private LocalDateTime createdAt;

    private LocalDate startDate;

    private LocalDate expireDate;

    @UniqueElements(message = ValidationUtil.UNIQUE_DATES)
    private List<LocalDate> attendedDays = new ArrayList<>();

    @PositiveOrZero(message = "Discount" + ValidationUtil.POSITIVE)
    private int discount;

    @Positive(message = "Price" + ValidationUtil.POSITIVE,
    groups = OnSave.class)
    private int finalPrice;

    @Transient
    private boolean isExpired;


    public boolean isExpired() {
        return !expireDate.isAfter(LocalDate.now());
//        return false;
    }

//    public int getFinalPrice() {
//        return subscriptionType.getPrice() - discount;
////        return 1;
//    }

//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = LocalDateTime.now();
//    }
//
//    public void setExpireDate(LocalDate expireDate) {
//        this.expireDate = startDate.plusDays(subscriptionType.getDurationInDays());

//    }


    @PrePersist
    @PreUpdate
    public void setDates() {
        createdAt = LocalDateTime.now();
        startDate = LocalDate.now();
        expireDate = startDate.plusDays(subscriptionType.getDurationInDays());
        finalPrice = calculateFinalPrice();
//        expireDate = LocalDate.now();
//        expireDate = startDate.plusMonths(1);
    }

    private int calculateFinalPrice() {
        return subscriptionType.getPrice() - discount;
    }

}
