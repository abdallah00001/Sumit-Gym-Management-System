package com.summit.gym.Sumit_Gym_Management_System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.summit.gym.Sumit_Gym_Management_System.validation.ValidationMessages.NOT_BLANK;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;



}
