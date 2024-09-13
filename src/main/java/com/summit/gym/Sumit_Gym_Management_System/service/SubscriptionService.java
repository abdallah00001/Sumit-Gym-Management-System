package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.MemberNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.SubscriptionRepo;
import com.summit.gym.Sumit_Gym_Management_System.utils.SecurityUtil;
import com.summit.gym.Sumit_Gym_Management_System.validation.ValidationUtil;
import com.summit.gym.Sumit_Gym_Management_System.validation.groups.OnSave;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepo subscriptionRepo;
    private final MemberRepo memberRepo;
    private final ShiftRepo shiftRepo;
    private final SecurityUtil securityUtil;
    private final ModelMapper modelMapper;

    private void validateAndSave(Subscription subscription) {
        if (subscription.getSubscriptionType().getPrice() <= subscription.getDiscount()) {
            throw new IllegalArgumentException(
                    "Price" + ValidationUtil.POSITIVE
            );
        }
        subscriptionRepo.save(subscription);
    }

    public List<SubscriptionDto> findAll() {
        return subscriptionRepo.findAll()
                .stream()
                .map(subscription -> modelMapper.map(subscription, SubscriptionDto.class))
                .toList();
    }


//    public void save(Subscription subscription,Long memberId) {
//        Member member = memberRepo.findById(memberId).orElseThrow(EntityNotFoundException::new);
//        Subscription latestSubscription = member.getLatestSubscription();
//
//        if (latestSubscription != null && !latestSubscription.isExpired()) {
//            throw new IllegalStateException("User already has an active subscription");
//        }
//
//        Long userId = securityUtil.extractUserIdFromSession();
//        subscription.getUser().setId(userId);
//        member.getSubscriptions().add(subscription);
//        Member savedMember = memberRepo.save(member);
//        Shift shift = shiftRepo.findById(1L).orElseThrow();
//        shift.getSubscriptions().add(subscription);
//        shiftRepo.save(shift);
//
//    }

    public void save(Subscription subscription, Long memberId) {
        Member member = memberRepo.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Subscription latestSubscription = member.getLatestSubscription();

        if (latestSubscription != null && !latestSubscription.isExpired()) {
            throw new IllegalStateException("User already has an active subscription");
        }

        Long userId = securityUtil.extractUserIdFromSession();
        subscription.getUser().setId(userId);
        member.getSubscriptions().add(subscription);
        subscription.setMember(member);
//        Shift shift = shiftRepo.findById(1L).orElseThrow();
//        shift.getSubscriptions().add(subscription);
        validateAndSave(subscription);
    }


    public List<SubscriptionDto> findSubscriptionsByCreateDate(LocalDate startDate,
                                                               LocalDate finishDate) {
        return subscriptionRepo.findByCreatedAtBetween(startDate, finishDate)
                .stream().map(
                        subscription -> modelMapper
                                .map(subscription, SubscriptionDto.class))
                .toList();
    }


    public int findTotalIncomeByDate(LocalDate startDate,
                                     LocalDate finishDate) {
        List<Subscription> subscriptions = subscriptionRepo.findByCreatedAtBetween(startDate, finishDate);
        return subscriptions
                .stream().mapToInt(Subscription::getFinalPrice)
                .sum();
    }


    public void addAttendedDay(Long memberId, LocalDate attendedDate) {
        // IN case added date is always date of day
        //        LocalDate attendedDate = LocalDate.now();

        //Have to fetch 1st to make sure our entity is up to date
        Member member = memberRepo.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Subscription latestSubscription = member.getLatestSubscription();
        if (latestSubscription == null) {
            throw new IllegalStateException("""
                    Member is new and has now subscriptions,
                    please add a subscription and try again
                    """);
        }
        if (latestSubscription.isExpired()) {
            throw new IllegalStateException("""
                    Member's subscription is expired,
                    please renew subscription and try again
                    """);
        }
        latestSubscription.getAttendedDays().add(attendedDate);
        subscriptionRepo.save(latestSubscription);

    }


    public List<SubscriptionDto> findByMember(Long memberId) {
        Member member = memberRepo.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<Subscription> subs = subscriptionRepo.findByMember(member);
        return subs.stream()
                .map(subscription -> modelMapper
                        .map(subscription, SubscriptionDto.class))
                .toList();
    }

    public List<SubscriptionDto> findByUserName(String userName) {
        return subscriptionRepo.findByUserName(userName)
                .stream().map(
                        subscription -> modelMapper
                                .map(subscription, SubscriptionDto.class))
                .toList();
    }

}
