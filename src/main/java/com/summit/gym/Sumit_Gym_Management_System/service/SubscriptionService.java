package com.summit.gym.Sumit_Gym_Management_System.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.WriterException;
import com.summit.gym.Sumit_Gym_Management_System.dto.MemberDto;
import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
import com.summit.gym.Sumit_Gym_Management_System.dto_mappers.MemberMapper;
import com.summit.gym.Sumit_Gym_Management_System.dto_mappers.SubscriptionMapper;
import com.summit.gym.Sumit_Gym_Management_System.enums.PaymentPurpose;
import com.summit.gym.Sumit_Gym_Management_System.enums.SubscriptionStatus;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.InvalidSubscriptionStatusException;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.MemberNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.*;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.*;
import com.summit.gym.Sumit_Gym_Management_System.specification.SubscriptionSpecification;
import com.summit.gym.Sumit_Gym_Management_System.utils.DateTimeFormatterUtil;
import com.summit.gym.Sumit_Gym_Management_System.utils.SessionAttributesManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static com.summit.gym.Sumit_Gym_Management_System.enums.SubscriptionStatus.*;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final static String NOT_FOUND_MESSAGE =
            "Subscription not found";

    private final SubscriptionRepo subscriptionRepo;
    private final MemberRepo memberRepo;
    private final SessionAttributesManager sessionAttributesManager;
    private final SubscriptionMapper subscriptionMapper;
    private final MemberMapper memberMapper;
    private final RefundRepo refundRepo;
    private final SubscriptionTypeRepo subscriptionTypeRepo;
    private final QrCodeService qrCodeService;
    private final PaymentRepo paymentRepo;
    private final SubscriptionSpecification subscriptionSpecification;

    public List<SubscriptionDto> findAll() {
        return subscriptionRepo.findAll()
                .stream()
                .map(subscriptionMapper::toSubscriptionDto)
                .toList();
    }

    public List<Subscription> findAllSubs() {
        return subscriptionRepo.findAll();
    }

    //    @Transactional(rollbackOn = Throwable.class)
/*    public String save(Subscription subscription, int paidTotal, Long subscriptionTypeId) {
        Shift shift;
        User user;
        Member member = subscription.getMember();
        Long memberID = member.getId();
        SubscriptionType subscriptionType = subscriptionTypeRepo.findById(subscriptionTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription type wasn't found."));
        subscription.setSubscriptionType(subscriptionType);
        String cashOutMessage = "";
        if (subscription.getPaymentType().equals(PaymentType.Cash)) {
            cashOutMessage = calculateCashOut(subscription, paidTotal);
        }

        if (subscription.getPrivateTrainer() != null && !subscription.getSubscriptionType().isForPrivateTrainer()) {
            throw new IllegalArgumentException("This subscription type does not support private trainers");
        } else if (subscription.getPrivateTrainer() == null && subscription.getSubscriptionType().isForPrivateTrainer()) {
            throw new IllegalArgumentException("Pleas add a private trainer");
        }


        //Checking if member is new or not
        if (memberID != null) {
            member = memberRepo.findById(memberID).orElseThrow(MemberNotFoundException::new);
            //Should never be null if member is not new
            Subscription latestSubscription = member.getLatestSubscription();
//            if (latestSubscription == null) {
//                throw new IllegalArgumentException("Member is not subscribed");
//            }
            if (latestSubscription != null) {
                SubscriptionStatus status = latestSubscription.getStatus();

                //If the latest sub is frozen or active can't create new one
                if (!status.equals(EXPIRED) && !status.equals(CANCELLED)) {
                    throw new IllegalStateException(
                            String.format("User already has an registered subscription that is %s", status)
                    );
                }
            }

        }

        //Manage bidirectional mappings
        shift = sessionAttributesManager.getCurrentShift();
        shift.getSubscriptions().add(subscription);
        user = sessionAttributesManager.getUser();
        subscription.setUser(user);
        member.addSubscription(subscription);
        subscription.setMember(member);
        Subscription savedSub = subscriptionRepo.save(subscription);
        Long savedMemberId = savedSub.getMember().getId();
        String savedQrLocation = null;
        try {
            savedQrLocation = qrCodeService.saveQrCodeToFile(savedMemberId);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
        WhatsAppMessengerService.sendCode(member.getPhone(), savedQrLocation);
        return cashOutMessage;
    }*/


    public void save(Payment payment, Long subscriptionTypeId, Member member) {
        Shift shift;
        User user;
        Long memberID = member.getId();
        PaymentPurpose paymentPurpose = PaymentPurpose.NEW_MEMBER;
        Subscription subscription = new Subscription();
        SubscriptionType subscriptionType = subscriptionTypeRepo.findById(subscriptionTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription type wasn't found."));
        subscription.setSubscriptionType(subscriptionType);

        if (subscription.getPrivateTrainer() != null && !subscription.getSubscriptionType().isForPrivateTrainer()) {
            throw new IllegalArgumentException("This subscription type does not support private trainers");
        } else if (subscription.getPrivateTrainer() == null && subscription.getSubscriptionType().isForPrivateTrainer()) {
            throw new IllegalArgumentException("Pleas add a private trainer");
        }


        //Checking if member is new or not
        if (memberID != null) {
            paymentPurpose = PaymentPurpose.CHANGE;
            member = memberRepo.findById(memberID).orElseThrow(MemberNotFoundException::new);
            //Should never be null if member is not new
            Subscription latestSubscription = member.getLatestSubscription();

            if (latestSubscription != null) {
                SubscriptionStatus status = latestSubscription.getStatus();

                //If the latest sub is frozen or active can't create new one
                if (!status.equals(EXPIRED) && !status.equals(CANCELLED)) {
                    throw new IllegalStateException(
                            String.format("User already has an registered subscription that is %s", status)
                    );
                }
            }
        }

        //Manage bidirectional mappings
        shift = sessionAttributesManager.getCurrentShift();
//        shift.getSubscriptions().add(subscription);
        shift.getPayments().add(payment);
        user = sessionAttributesManager.getUser();
        Subscription savedSub = manageAndSaveSub(payment, member, subscription, paymentPurpose,user);
        Long savedMemberId = savedSub.getMember().getId();
        String savedQrLocation = null;
        try {
            savedQrLocation = qrCodeService.saveQrCodeToFile(savedMemberId);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
        WhatsAppMessengerService.sendCode(member.getPhone(), savedQrLocation);
    }

    private Subscription manageAndSaveSub(Payment payment, Member member, Subscription subscription, PaymentPurpose paymentPurpose, User user) {
        subscription.setUser(user);
        member.addSubscription(subscription);
        payment.setSubscription(subscription);
        payment.setPurpose(paymentPurpose);
//        payment.setUser(user);
        subscription.getPayments().add(payment);
        subscription.setMember(member);
        Subscription savedSub = subscriptionRepo.save(subscription);
        return savedSub;
    }

    public List<SubscriptionDto> findSubscriptionsByCreateDate(LocalDate startDate,
                                                               LocalDate finishDate) {
        return subscriptionRepo.findByCreatedAtBetween(startDate, finishDate)
                .stream().map(subscriptionMapper::toSubscriptionDto)
                .toList();
    }


    public String renew(Payment payment, Long subscriptionId) {
        Subscription subscription = subscriptionRepo.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        SubscriptionStatus status = subscription.getStatus();

        if (!status.equals(EXPIRED) && !status.equals(CANCELLED)) {
            throw new IllegalStateException(
                    String.format("User already has an registered subscription that is %s", status)
            );
        }
        Subscription newSub = new Subscription();
        newSub.setSubscriptionType(subscription.getSubscriptionType());
        manageAndSaveSub(payment, subscription.getMember(), newSub, PaymentPurpose.RENEW, subscription.getUser());

        return "Subscription renewed successfully";
    }


    public int findTotalIncomeByDate(LocalDate startDate,
                                     LocalDate finishDate) {
//        List<Subscription> subscriptions = subscriptionRepo.findByCreatedAtBetween(startDate, finishDate);
        List<? extends PaymentUnit> paymentUnits;
        paymentUnits = paymentRepo.findByCreatedAtBetween(startDate, finishDate);
        return paymentUnits
                .stream().mapToInt(PaymentUnit::getFinalPrice)
                .sum();
    }

    private static void validateStatus(Subscription latestSubscription) {
        if (latestSubscription == null) {
            throw new IllegalStateException("""
                    Member is new and has now subscriptions,
                    please add a subscription and try again
                    """);
        }

        SubscriptionStatus status = latestSubscription.getStatus();
        if (!status.equals(ACTIVE)) {
            if (status.equals(FROZEN)) {
                throw new InvalidSubscriptionStatusException(status,
                        "Pleas unfreeze to add new sessions");
            }
            throw new InvalidSubscriptionStatusException(status);
        }

    }

    public MemberDto addAttendedDay(Long memberId, LocalDate attendedDate) {

        if (attendedDate == null) attendedDate = LocalDate.now();

        //Have to fetch 1st to make sure our entity is up to date
        Member member = memberRepo.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Subscription latestSubscription = member.getLatestSubscription();
        //Make sure sub is active
        validateStatus(latestSubscription);
        latestSubscription.getAttendedDays().add(attendedDate);
        subscriptionRepo.save(latestSubscription);
        return memberMapper.mapToMemberDto(member);
    }


    public List<SubscriptionDto> findByMember(Long memberId) {
        Member member = memberRepo.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<Subscription> subs = subscriptionRepo.findByMember(member);
        return subs.stream()
                .map(subscriptionMapper::toSubscriptionDto)
                .toList();
    }

    public List<SubscriptionDto> findByUserName(String userName) {
        return subscriptionRepo.findByUserName(userName)
                .stream().map(subscriptionMapper::toSubscriptionDto)
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


    public List<SubscriptionDto> filter(Long memberId, Long coachId
            , LocalDate startDate, LocalDate finishDate, SubscriptionStatus status) {

        List<Subscription> subscriptions = subscriptionRepo
                .findAll(subscriptionSpecification.getSpecs(memberId,coachId, startDate, finishDate));
        subscriptions = subscriptions.stream()
                .filter(subscription -> subscription.getStatus().equals(status))
                        .toList();
        return subscriptions.stream()
                .map(subscriptionMapper::toSubscriptionDto).toList();
    }

    //Contains 2 save operations so @Transactional is used
    @Transactional
    public String refund(Long subscriptionId, int moneyRefunded) {
        Subscription subscription = subscriptionRepo.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find subscription"));

        if (subscription.getLatestPayment().getFinalPrice() < moneyRefunded) {
            throw new IllegalArgumentException(
                    "Refunded money can't be more than the subscription price"
            );
        }

        SubscriptionStatus status = subscription.getStatus();
        if (status.equals(CANCELLED) || status.equals(EXPIRED)) {
            throw new InvalidSubscriptionStatusException(
                    status, "Subscription is already %s".formatted(status)
            );
        }

        Shift shift = sessionAttributesManager.getCurrentShift();
        subscription.setCancelled(true);
        Refund refund = new Refund();
        refund.setMoneyRefunded(moneyRefunded);
        refund.setSubscription(subscription);
        Refund savedRefund = refundRepo.save(refund);
        shift.getRefunds().add(savedRefund);
        subscriptionRepo.save(subscription);
        return "Refund completed successfully";
    }

    public static void main(String[] args) throws JsonProcessingException {
        LocalDate exp = LocalDate.of(2025, 5, 12);
        System.out.println(Period.between(LocalDate.now(), exp));
        Period amountToAdd = Period.of(0, 3, 0);
        Period period = Period.ofDays(50);

        System.out.println(DateTimeFormatterUtil.periodToString(amountToAdd));
        System.out.println(DateTimeFormatterUtil.periodToString(period));

    }


}
