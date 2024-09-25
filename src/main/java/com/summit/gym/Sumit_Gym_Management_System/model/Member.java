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

import java.util.ArrayList;
import java.util.List;

import static com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil.NOT_BLANK;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "subscriptions")
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(name = ValidationUtil.UNIQUE_PHONE_CONSTRAINT,
                columnNames = "phone")
})
public class Member {

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

//    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @JsonIgnore
//    @JsonIgnoreProperties("member")
    @OneToMany
            (cascade = CascadeType.ALL)
    private List<Subscription> subscriptions = new ArrayList<>();

    @JsonIgnore
    public Subscription getLatestSubscription() {
        return  !subscriptions.isEmpty() ?
                subscriptions.getLast()
                : null;
    }


}
