package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import com.summit.gym.Sumit_Gym_Management_System.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sub")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Get all subscriptions")
    @GetMapping
    public List<SubscriptionDto> findAll() {
        return subscriptionService.findAll();
    }

    @Operation(summary = "Save new Subscription")
    @PostMapping("/member/{memberId}")
    public ResponseEntity<String> save(@PathVariable Long memberId,
                                       @RequestBody @Valid Subscription subscription) {
        subscriptionService.save(subscription, memberId);
        return ResponseEntity
                .ok("Subscription created successfully");
    }

    @Operation(summary = "Get total income between range of 2 dates")
    @GetMapping("admin/income/{start}/{finish}")
    public int calculateIncomeByDate(@PathVariable LocalDate start,
                                     @PathVariable LocalDate finish) {
        return subscriptionService.findTotalIncomeByDate(start, finish);
    }

    @Operation(summary = "Get Subscriptions created in provided date range")
    @GetMapping("/{start}/{finish}")
    public List<SubscriptionDto> findByCreatedDate(@PathVariable LocalDate start,
                                                @PathVariable LocalDate finish) {
        return subscriptionService.findSubscriptionsByCreateDate(start, finish);
    }

    @Operation(summary = "Add date to attended days of Sub")
    @PutMapping("member/{memberId}/day/{date}")
    public ResponseEntity<String> addDay(@PathVariable
                       @PastOrPresent(message = "Date must be past or present")
                       LocalDate date,
                       @PathVariable Long memberId) {
        subscriptionService.addAttendedDay(memberId, date);
        return ResponseEntity
                .ok("Day added successfully");
    }

    @Operation(summary = "Get ALL subs saved by a certain user by his user name")
    @GetMapping("/user/{userName}")
    public List<SubscriptionDto> findAllByUserName(@PathVariable String userName) {
        return subscriptionService.findByUserName(userName);
    }

    @Operation(summary = "Get All subs that belong to a certain member")
    @GetMapping("/member/{memberId}")
    public List<SubscriptionDto> findAllByMember(@PathVariable Long memberId) {
        return subscriptionService.findByMember(memberId);
    }


}
