package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping
    public List<Shift> findAll() {
        return shiftService.findAll();
    }

    @PostMapping("/sub/{memberId}")
    public ResponseEntity<String> addSubscription(@RequestBody Subscription subscription,
                                                  @PathVariable Long memberId) {
        shiftService.addSubscription(subscription, memberId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Saved successfully");
    }

    @PostMapping
    public void startNewShift(@RequestBody Shift shift) {
        shiftService.startNewShift(shift);
    }

    @PutMapping("/close/{counterMoney}")
    public ResponseEntity<String> closeShift(@PathVariable int counterMoney) {
        String message = shiftService.closeShift(counterMoney);
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.getAuthentication().setAuthenticated(false);
        return ResponseEntity.ok(message);
    }


}
