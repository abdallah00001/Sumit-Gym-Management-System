package com.summit.gym.Sumit_Gym_Management_System.dto;

import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto extends BaseDto{

    private Long id;
    private String userName;
    private Role role;


}
