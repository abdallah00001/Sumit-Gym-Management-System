package com.summit.gym.Sumit_Gym_Management_System.dto_mappers;


import com.summit.gym.Sumit_Gym_Management_System.dto.UserDto;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper{


    public UserMapper(ModelMapper modelMapper, UserRepo userRepo) {
        super(modelMapper);
    }

    public UserDto mapToUserDto(User user) {
        return mapToDto(user, UserDto.class);
    }

}
