package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.model.Refund;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.RefundRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.SubscriptionRepo;
import com.summit.gym.Sumit_Gym_Management_System.utils.SessionAttributesManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundRepo refundRepo;
    private final SubscriptionRepo subscriptionRepo;
    private final SessionAttributesManager sessionAttributesManager;

//    public String refund(Long subscriptionId, int moneyRefunded) {
//        Subscription subscription = subscriptionRepo.findById(subscriptionId)
//                .orElseThrow(() -> new EntityNotFoundException("Couldn't find subscription"));
//
//        if (subscription.getFinalPrice() < moneyRefunded) {
//            throw new IllegalArgumentException(
//                    "Refunded money can't be more than the subscription price"
//            );
//        }
//
//        subscription.setCancelled(true);
//        Refund refund = new Refund();
//        refund.setMoneyRefunded(moneyRefunded);
//        refund.setSubscription(subscription);
//
//    }


}
