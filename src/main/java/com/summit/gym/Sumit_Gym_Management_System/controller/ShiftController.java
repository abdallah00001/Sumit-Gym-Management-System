package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.service.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/sub")
    public ResponseEntity<String> addSubscription(@RequestBody Subscription subscription) {
        shiftService.addSubscription(subscription);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Saved successfully");
    }

    @PostMapping
    public void startNewShift(@RequestBody Shift shift) {
        shiftService.startNewShift(shift);
    }


}
