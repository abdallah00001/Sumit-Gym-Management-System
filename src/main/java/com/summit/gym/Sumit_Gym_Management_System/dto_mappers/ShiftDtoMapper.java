package com.summit.gym.Sumit_Gym_Management_System.dto_mappers;

import com.summit.gym.Sumit_Gym_Management_System.dto.ShiftDto;
import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShiftDtoMapper extends BaseMapper{

    public ShiftDtoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    public ShiftDto toShiftDto(Shift shift) {
        return mapToDto(shift, ShiftDto.class);
    }

}
