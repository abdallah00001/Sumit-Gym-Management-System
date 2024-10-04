package com.summit.gym.Sumit_Gym_Management_System.dto_mappers;

import com.summit.gym.Sumit_Gym_Management_System.dto.BaseDto;
import com.summit.gym.Sumit_Gym_Management_System.model.BaseEntity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public abstract class BaseMapper {

    protected final ModelMapper modelMapper;

    protected <T extends BaseDto> T mapToDto(BaseEntity entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    protected <E extends BaseEntity> E mapToEntity(BaseDto dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    protected void addModelMapperConverters(Converter<?, ?>... converters) {
        for (Converter<?, ?> converter : converters) {
        modelMapper.addConverter(converter);
        }
    }

}
