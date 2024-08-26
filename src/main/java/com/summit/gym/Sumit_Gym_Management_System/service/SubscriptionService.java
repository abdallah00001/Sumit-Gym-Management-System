package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepo subscriptionRepo;


    public List<Subscription> findAll() {
        return subscriptionRepo.findAll();
    }

    public void save(Subscription subscription) {
        subscriptionRepo.save(subscription);
    }

}
