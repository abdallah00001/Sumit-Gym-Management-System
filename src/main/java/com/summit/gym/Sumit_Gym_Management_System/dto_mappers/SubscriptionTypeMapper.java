package com.summit.gym.Sumit_Gym_Management_System.dto_mappers;

import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionTypeDto;
import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionTypeSummaryDto;
import com.summit.gym.Sumit_Gym_Management_System.enums.PeriodType;
import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import com.summit.gym.Sumit_Gym_Management_System.utils.DateTimeFormatterUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Period;

@Component
public class SubscriptionTypeMapper extends BaseMapper {

    public SubscriptionTypeMapper(ModelMapper modelMapper) {
        super(modelMapper);
        modelMapper.typeMap(SubscriptionType.class, SubscriptionTypeDto.class)
                .addMappings(mapping -> {
                    mapping.using(periodStringConverter)
                            .map(SubscriptionType::getPeriod,
                                    SubscriptionTypeDto::setPeriodString);
                });
    }


    public SubscriptionTypeSummaryDto toSummaryDto(SubscriptionType subscriptionType) {
        return mapToDto(subscriptionType, SubscriptionTypeSummaryDto.class);
    }

    public SubscriptionTypeDto toSubscriptionTypeDto(SubscriptionType subscriptionType) {
        return mapToDto(subscriptionType, SubscriptionTypeDto.class);
    }


    private Converter<Period, String> periodStringConverter = context ->
            DateTimeFormatterUtil.periodToString(context.getSource());


}
