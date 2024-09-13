package com.summit.gym.Sumit_Gym_Management_System.model;

import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(name = ValidationUtil.UNIQUE_PHONE_CONSTRAINT,
                columnNames = "phone"),
        @UniqueConstraint(name = ValidationUtil.UNIQUE_NAME_CONSTRAINT,
                columnNames = "name")
})
public class Coach {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String phone;

    private int sessionPrice;
}
