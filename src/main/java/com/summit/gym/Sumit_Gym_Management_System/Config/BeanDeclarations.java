//package com.summit.gym.Sumit_Gym_Management_System.Config;
//
//import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
//import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
//import com.summit.gym.Sumit_Gym_Management_System.utils.DateTimeFormatterUtil;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.Converter;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.spi.MappingContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//@RequiredArgsConstructor
//public class BeanDeclarations {
//
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.typeMap(Subscription.class, SubscriptionDto.class)
//                .addMappings(mapper -> mapper.map(Subscription::getUser, SubscriptionDto::setUserDto));
//
//        modelMapper.addConverter(new Converter<LocalDateTime, String>() {
//            @Override
//            public String convert(MappingContext<LocalDateTime, String> context) {
//                return DateTimeFormatterUtil.formatLocalDateTime(context.getSource());
//            }
//        });
//
//        //To perform partial updates
//        modelMapper.getConfiguration().setSkipNullEnabled(true);
//        return modelMapper;
//    }
//
//
//}
