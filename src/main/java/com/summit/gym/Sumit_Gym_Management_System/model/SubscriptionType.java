package com.summit.gym.Sumit_Gym_Management_System.model;

import com.summit.gym.Sumit_Gym_Management_System.enums.PeriodType;
import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.ValidSubscriptionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.Period;

@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ValidSubscriptionType
public class SubscriptionType extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = ValidationUtil.NOT_BLANK)
    private String name;

    @PositiveOrZero(message = "Price" + ValidationUtil.POSITIVE_OR_ZERO)
    private int price;

//    @Min(value = 1,message = "Period" + ValidationUtil.POSITIVE)
    private int periodLength;

    @PositiveOrZero(message = "Freeze days" + ValidationUtil.POSITIVE_OR_ZERO)
    private int allowedFreezeDays;

    @NotNull(message = "Monthly or Daily" + ValidationUtil.NOT_NULL)
    private PeriodType periodType;

    private boolean forPrivateTrainer;


    //Use of period for day count accuracy
    //ex: 1 month can have 29 days while another can have 31
    public Period getPeriod() {
        Period period;
        if (periodType.equals(PeriodType.Monthly)) {
            period = Period.ofMonths(periodLength);
        } else {
            period = Period.ofDays(periodLength);
        }
        return period;
    }

//    public int getPeriodInDays() {
//        return getPeriod().getDays();
//    }




}
