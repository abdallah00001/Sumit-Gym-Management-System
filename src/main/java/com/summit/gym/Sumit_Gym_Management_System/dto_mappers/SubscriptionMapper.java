package com.summit.gym.Sumit_Gym_Management_System.dto_mappers;

import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionForMemberDto;
import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionTypeSummaryDto;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.utils.DateTimeFormatterUtil;
import org.modelmapper.Converter;
import org.modelmapper.ExpressionMap;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SubscriptionMapper extends BaseMapper {

    private final SubscriptionTypeMapper subscriptionTypeMapper;

    public SubscriptionMapper(ModelMapper modelMapper, SubscriptionTypeMapper subscriptionTypeMapper) {
        super(modelMapper);
        this.subscriptionTypeMapper = subscriptionTypeMapper;

//        modelMapper.typeMap(Subscription.class, SubscriptionDto.class)
//                .addMappings(getSubscriptionDtoExpressionMap());
        modelMapper.addConverter(toSubForMemberDtoConverter());
    }


    public SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return mapToDto(subscription, SubscriptionDto.class);
    }

    public SubscriptionForMemberDto toSubForMemberDto(Subscription subscription) {
        return mapToDto(subscription, SubscriptionForMemberDto.class);
    }

    private Converter<Subscription, SubscriptionForMemberDto> toSubForMemberDtoConverter() {
        return new Converter<>() {
            @Override
            public SubscriptionForMemberDto convert(MappingContext<Subscription, SubscriptionForMemberDto> context) {
                Subscription subscription = context.getSource();
                if (subscription == null) {
                    return null;
                }

                SubscriptionForMemberDto dto = new SubscriptionForMemberDto();
                SubscriptionTypeSummaryDto summaryDto = subscriptionTypeMapper.toSummaryDto(subscription.getSubscriptionType());

                dto.setId(subscription.getId());
                dto.setStartDate(subscription.getStartDate());
                dto.setExpireDate(subscription.getExpireDate());
                dto.setPassedPeriodString(DateTimeFormatterUtil.periodToString(subscription.getpassedPeriod()));
                dto.setRemainingPeriodString(DateTimeFormatterUtil.periodToString(subscription.getRemainingPeriod()));
                dto.setTypeSummaryDto(summaryDto);
                dto.setSessionsAttended(subscription.getAttendedDaysCount());
                return dto;
            }
        };
    }

    protected static ExpressionMap<Subscription, SubscriptionDto> getSubscriptionDtoExpressionMap() {
        return mapping -> {
            mapping.map(Subscription::getUser, SubscriptionDto::setUserDto);

            mapping.map(Subscription::getCreatedAt, SubscriptionDto::setCreatedAtString);

            mapping.map(Subscription::getAttendedDaysCount,
                    SubscriptionDto::setAttendedDaysCount);


            mapping.map(Subscription::getSubscriptionType,
                    SubscriptionDto::setSubscriptionTypeDto);


            // Format `startDate` and `expireDate` similarly if needed
//            mapping.map(source -> DateTimeFormatterUtil.formatLocalDate(source.getStartDate()),
//                    SubscriptionDto::setFormattedStartDate);
//            mapping.map(source -> DateTimeFormatterUtil.formatLocalDate(source.getExpireDate()),
//                    SubscriptionDto::setFormattedExpireDate);

        };
    }



}
