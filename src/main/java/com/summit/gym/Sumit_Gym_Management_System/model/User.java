package com.summit.gym.Sumit_Gym_Management_System.model;

import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.summit.gym.Sumit_Gym_Management_System.validation.ValidationMessages.NOT_BLANK;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_name",columnNames = "userName")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "user name" + NOT_BLANK)
    @Size(min = 3, max = 20,
    message = "user name must be between 3-20 characters")
    private String userName;

    @NotBlank(message = "password" + NOT_BLANK)
    @Size(min = 5, message = "password must be at least 5 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


}
