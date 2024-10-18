package com.summit.gym.Sumit_Gym_Management_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.summit.gym.Sumit_Gym_Management_System.enums.Gender;
import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil.NOT_BLANK;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "latestSubscription")
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(name = ValidationUtil.UNIQUE_PHONE_CONSTRAINT,
                columnNames = "phone")
})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "name" + NOT_BLANK)
    @Size(min = 3, max = 50,
            message = "name must be 3-50 characters")
    private String name;

    @NotBlank(message = "Phone" + NOT_BLANK)
    @Phone
    private String phone;

    @NotNull(message = "Gender" + NOT_BLANK)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @JsonIgnore
    @OneToMany
    private List<Subscription> subscriptions = new ArrayList<>();

//    @JsonIgnore
//    public Subscription getLatestSubscription() {
//        return subscriptions.getLast();
//    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    @JsonIgnore
    public Subscription getLatestSubscription() {
        int size = subscriptions.size();
        Subscription currentSub = null;
        for (int i = size - 1; i >= 0; i--) {
            currentSub = subscriptions.get(i);
            LocalDate startDate = currentSub.getStartDate();

            if (startDate.isBefore(LocalDate.now()) || startDate.isEqual(LocalDate.now())) {
                break;
            }
        }
        return currentSub;
    }

    @JsonIgnore
    public Subscription getPendingSubscription() {
        if (subscriptions.isEmpty()) {
            return null;
        }
        Subscription pendingSup = subscriptions.getLast();
        if (pendingSup.getStartDate().isAfter(LocalDate.now())) {
            return pendingSup;
        }
        return null;
    }


}


//    @JsonIgnore
////    @JsonIgnoreProperties("member")
//    @OneToMany
//            (cascade = CascadeType.ALL)
//    private List<Subscription> subscriptions = new ArrayList<>();

//    @JsonIgnore
//    public Subscription getLatestSubscription() {
//        return  !subscriptions.isEmpty() ?
//                subscriptions.getLast()
//                : null;
//    }
