package com.summit.gym.Sumit_Gym_Management_System.specification;

import com.summit.gym.Sumit_Gym_Management_System.model.*;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.CoachRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 FILTERS:
 * member.
 * paymentType.
 * user.
 * trainer.
 * create.
 * expired
 * frozen
 */
@Component
@RequiredArgsConstructor
public class SubscriptionSpecification {

    private final  MemberRepo memberRepo;
    private final CoachRepo coachRepo;
    private final UserRepo userRepo;

    private Specification<Subscription> hasMember(Long memberId) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> {
            Member member = memberRepo.findById(memberId).orElse(null);
            if (member == null) {
                return null;
            }
            return builder.equal(root.get("member"), member);
        };
    }

//    private Specification<Subscription> hasPaymentType(PaymentType paymentType) {
//        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
//                -> {
//            if (paymentType == null) {
//                return null;
//            }
//            return builder.equal(root.get("paymentType"), paymentType);
//        };
//    }

    private Specification<Subscription> hasTrainer(Long coachId) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> {
            Coach coach = coachRepo.findById(coachId).orElse(null);
            if (coach == null) {
                return null;
            }
            return builder.equal(root.get("coach"), coach);
        };
    }

//    private Specification<Subscription> createdByUser(User user) {
//        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
//                -> CommonSpecifications.createdByUSer(root, builder, user, "user");
//    }


    private Specification<Subscription> createdBetween(LocalDate startDate, LocalDate finishDate) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> CommonSpecifications.createdBetween(root, builder, startDate, finishDate, "createdAt");
    }

    private Specification<Subscription> isExpire(Boolean isExpiredBool) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> CommonSpecifications.booleanStatus(root, builder, isExpiredBool, "isExpired");
    }

    private Specification<Subscription> isFrozen(Boolean isFrozenBool) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> CommonSpecifications.booleanStatus(root, builder, isFrozenBool, "isFrozen");
    }


    public Specification<Subscription> getSpecs(Long memberId, Long coachId
            , LocalDate startDate, LocalDate finishDate) {
        return Specification
                .where(hasMember(memberId))
//                .and(hasPaymentType(paymentType))
                .and(hasTrainer(coachId))
//                .and(createdByUser(userId))
                .and(createdBetween(startDate, finishDate));
//                .and(isExpire(isExpiredBool))
//                .and(isFrozen(isFrozenBool));
    }


}
