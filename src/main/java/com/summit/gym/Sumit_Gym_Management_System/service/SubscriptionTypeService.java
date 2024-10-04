package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionTypeDto;
import com.summit.gym.Sumit_Gym_Management_System.dto_mappers.SubscriptionTypeMapper;
import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.SubscriptionTypeRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionTypeService {

    private final SubscriptionTypeRepo subscriptionTypeRepo;
    private final SubscriptionTypeMapper subscriptionTypeMapper;
    private final String notFoundMessage = "Subscription type doesn't exist";

    public List<SubscriptionTypeDto> findAll() {
        return subscriptionTypeRepo.findAll()
                .stream().map(subscriptionTypeMapper::toSubscriptionTypeDto)
                .toList();
    }

    public SubscriptionType findById(Long id) {
        return subscriptionTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));
    }

    public void save(SubscriptionType subscriptionType) {
        subscriptionTypeRepo.save(subscriptionType);
    }

    public String update(Long oldTypeId, SubscriptionType updatedType) {
        return null;
    }

    public void delete(Long id) {
        subscriptionTypeRepo.deleteById(id);
    }

}
