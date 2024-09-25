package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.MemberNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.SubscriptionRepo;
import com.summit.gym.Sumit_Gym_Management_System.utils.SessionAttributesManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final static String NOT_FOUND_MESSAGE =
            "Subscription not found";

    private final SubscriptionRepo subscriptionRepo;
    private final MemberRepo memberRepo;
    private final ShiftRepo shiftRepo;
    private final SessionAttributesManager sessionAttributesManager;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;


    private void validateAndSave(Subscription subscription) {
//        if (subscription.getFinalPrice() <= 0) {
//            throw new IllegalArgumentException(
//                    "Price" + ValidationUtil.POSITIVE
//            );
//        }
        subscriptionRepo.save(subscription);
    }

    public List<SubscriptionDto> findAll() {
        return subscriptionRepo.findAll()
                .stream()
                .map(subscription -> modelMapper.map(subscription, SubscriptionDto.class))
                .toList();
    }


    @Transactional
    public void save(Subscription subscription, Long memberId) {
//        Member member = memberRepo.findById(memberId).orElseThrow(MemberNotFoundException::new);

//        Member member = memberRepo.findById(memberId).orElse(newMember);
        Shift shift;
        User user;

        Member member = subscription.getMember();

        Long memberID = member.getId();
        if (memberID != null) {
            member = entityManager.merge(subscription.getMember());
            Subscription latestSubscription = member.getLatestSubscription();

            if (latestSubscription != null && !latestSubscription.isExpired()) {
                throw new IllegalStateException("User already has an active subscription");
            }

        }

        //Manage bidirectional mappings
//        Shift shift = shiftRepo.findById(1L).orElseThrow();
        shift = sessionAttributesManager.getCurrentShift();
        shift.getSubscriptions().add(subscription);
        user = sessionAttributesManager.getUser();
        subscription.setUser(user);
        member.getSubscriptions().add(subscription);
        subscription.setMember(member);
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


    private Subscription findSubscriptionById(Long subscriptionId) {
        return subscriptionRepo.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
    }

    public String freezeSubscription(Long subscriptionId, int daysToFreeze) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        subscription.freeze(daysToFreeze);
        subscriptionRepo.save(subscription);
        return String.format("""
                        Subscription freeze completed successfully,
                        Expire date: %s
                        remaining freeze days limit: %d
                        """
                , subscription.getExpireDate().toString(),
                subscription.getRemainingFreezeLimitCount());
    }

    public String unFreezeSubscription(Long subscriptionId) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        subscription.unFreeze();
        subscriptionRepo.save(subscription);

        return String.format("""
                        Subscription successfully unfrozen,
                        Expires at: %s
                        Remaining allowed freeze days: %d
                        """
                , subscription.getExpireDate().toString(),
                subscription.getRemainingFreezeLimitCount());
    }


}
