package com.summit.gym.Sumit_Gym_Management_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.summit.gym.Sumit_Gym_Management_System.enums.PaymentPurpose;
import com.summit.gym.Sumit_Gym_Management_System.enums.PaymentType;
import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment extends PaymentUnit{

    @Id
    @GeneratedValue
    private Long id;

    private int originalPrice;

    @ManyToOne
    private User user;

    @NotNull(message = "Payment type" + ValidationUtil.NOT_NULL)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    private LocalDateTime createdAt;

    private int discount;

//    @PositiveOrZero(message = "Price" + ValidationUtil.POSITIVE_OR_ZERO)
    private int finalPrice;

    @JsonIgnore
    @ManyToOne
    private Subscription subscription;

    private PaymentPurpose purpose;


    private int calculateFinalPrice() {
        return originalPrice - discount;
    }

    @PrePersist
    public void setup() {
        createdAt = LocalDateTime.now();
        finalPrice = calculateFinalPrice();
    }

}
