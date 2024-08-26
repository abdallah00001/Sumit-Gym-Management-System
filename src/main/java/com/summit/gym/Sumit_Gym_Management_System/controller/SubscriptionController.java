package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sub")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<Subscription> findAll() {
        return subscriptionService.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Subscription subscription) {
        subscriptionService.save(subscription);
        return ResponseEntity.ok().build();
    }

}
