package com.summit.gym.Sumit_Gym_Management_System.service;

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
    private final String notFoundMessage = "Subscription type doesn't exist";

    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepo.findAll();
    }

    public SubscriptionType findById(Long id) {
        return subscriptionTypeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));
    }

    public void save(SubscriptionType subscriptionType) {
        subscriptionTypeRepo.save(subscriptionType);
    }

    public void deleteById(Long id) {
        if (!subscriptionTypeRepo.existsById(id)) {
            throw new EntityNotFoundException(notFoundMessage);
        }
        subscriptionTypeRepo.deleteById(id);
    }

}
