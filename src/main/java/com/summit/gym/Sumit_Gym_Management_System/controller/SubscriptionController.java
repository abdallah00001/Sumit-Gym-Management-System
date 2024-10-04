package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.dto.MemberDto;
import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
import com.summit.gym.Sumit_Gym_Management_System.enums.PaymentType;
import com.summit.gym.Sumit_Gym_Management_System.model.Coach;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.SubscriptionRepo;
import com.summit.gym.Sumit_Gym_Management_System.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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


    @GetMapping("/s")
    public List<Subscription> findAllSubs() {
        return subscriptionService.findAllSubs();
    }

    @Operation(summary = "Get all subscriptions")
    @GetMapping
    public List<SubscriptionDto> findAll() {
        return subscriptionService.findAll();
    }

    //TODO: MODIFY IN JS
    @Operation(summary = "Save new Subscription")
    @PostMapping("/sub-type/{subTypeId}/cashout/{paidTotal}")
    public ResponseEntity<String> save(@PathVariable int paidTotal,
                                       @PathVariable
                                       @Positive(message = "Please select a valid subscription type")
                                       Long subTypeId,
                                       @RequestBody @Valid Subscription subscription) {
        String cashOutMessage = subscriptionService.save(subscription, paidTotal, subTypeId);
        return ResponseEntity
                .ok("Subscription created successfully\n" + cashOutMessage);
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

    //TODO: RETURN MEMBER
    @Operation(summary = "Add date to attended days of Sub")
    @PutMapping("member/{memberId}/day/{date}")
    public ResponseEntity<MemberDto> addDay(@PathVariable
                                         @PastOrPresent(message = "Date must be past or present")
                                         LocalDate date,
                                            @PathVariable Long memberId) {
        MemberDto memberDto = subscriptionService.addAttendedDay(memberId, date);
        return ResponseEntity
                .ok(memberDto);
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

    @PutMapping("/freeze/{subscriptionId}/{daysToFreeze}")
    public ResponseEntity<String> freeze(@PathVariable Long subscriptionId,
                                         @PathVariable int daysToFreeze) {
        String message = subscriptionService.freezeSubscription(subscriptionId, daysToFreeze);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/unfreeze/{subscriptionId}")
    public ResponseEntity<String> unfreeze(@PathVariable Long subscriptionId) {
        String message = subscriptionService.unFreezeSubscription(subscriptionId);
        return ResponseEntity.ok(message);
    }

    //////////////////////////////////////////////////
    private final SubscriptionRepo subscriptionRepo;

    @PutMapping("/exp/{id}")
    public void expire(@PathVariable Long id) {
        Subscription subscription = subscriptionRepo.findById(id).orElseThrow(EntityNotFoundException::new);
//        subscription.setExpired(true);
        subscriptionRepo.save(subscription);
    }

    @PutMapping("/refund/{subId}/{moneyRefunded}")
    public ResponseEntity<String> refund(@PathVariable Long subId,
                                         @PathVariable int moneyRefunded) {
        String message = subscriptionService.refund(subId, moneyRefunded);
        return ResponseEntity.ok(message);
    }

    ////////////////////////////////////////////////////
    @GetMapping("/filter")
    public List<SubscriptionDto> filterSubscriptions(
            @RequestParam(required = false) Member member,
            @RequestParam(required = false) PaymentType paymentType,
            @RequestParam(required = false) User user,
            @RequestParam(required = false) Coach coach,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate finishDate,
            @RequestParam(required = false) Boolean isExpired,
            @RequestParam(required = false) Boolean isFrozen
    ) {

        return subscriptionService.filter(member, paymentType, coach, user,
                startDate, finishDate, isExpired, isFrozen);

    }


}
