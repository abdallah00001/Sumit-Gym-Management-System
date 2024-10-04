package com.summit.gym.Sumit_Gym_Management_System.model;

import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil.NOT_BLANK;

@EqualsAndHashCode(callSuper = false)
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
public class Coach extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name" + ValidationUtil.NOT_BLANK)
    private String name;

    @NotBlank(message = "Phone" + NOT_BLANK)
    @Phone
    private String phone;

//    private int sessionPrice;
}
