package com.summit.gym.Sumit_Gym_Management_System.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.summit.gym.Sumit_Gym_Management_System.enums.PaymentType;
import com.summit.gym.Sumit_Gym_Management_System.enums.SubscriptionStatus;
import com.summit.gym.Sumit_Gym_Management_System.model.Freeze;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter
@Data
public class SubscriptionDto extends BaseDto{

    //TODO::
    // 1-use subTypeDto
//     2-format date time
    // 3-Member summary dto
    // 4- attended days cont

    private Long id;
    @JsonIgnoreProperties("latestSubscription")
    private Member member;
    private PaymentType paymentType;
    private UserDto userDto;
    private SubscriptionTypeDto subscriptionTypeDto;
    private String createdAtString;
    private LocalDate startDate;
    private LocalDate expireDate;
    //    private List<LocalDate> attendedDays = new ArrayList<>();
    private int attendedDaysCount;
    private int discount;
    private int finalPrice;
    private String notes;
    private SubscriptionStatus status;
    private List<Freeze> freezeHistory;
    private List<Payment> payments;

}
