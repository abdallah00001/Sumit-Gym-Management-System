package com.summit.gym.Sumit_Gym_Management_System.Config;

import com.summit.gym.Sumit_Gym_Management_System.dto.MemberWithAttendanceDto;
import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeanDeclarations {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Subscription.class, SubscriptionDto.class)
                .addMappings(mapper -> mapper.map(Subscription::getUser, SubscriptionDto::setUserDto));

        modelMapper.addConverter(MemberWithAttendanceDto.toDtoConverter);
        return modelMapper;
    }


}
