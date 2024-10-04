package com.summit.gym.Sumit_Gym_Management_System.specification;

import com.summit.gym.Sumit_Gym_Management_System.enums.PaymentType;
import com.summit.gym.Sumit_Gym_Management_System.model.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

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
public class SubscriptionSpecification {


    private static Specification<Subscription> hasMember(Member member) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> {
            if (member == null) {
                return null;
            }
            return builder.equal(root.get("member"), member);
        };
    }

    private static Specification<Subscription> hasPaymentType(PaymentType paymentType) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> {
            if (paymentType == null) {
                return null;
            }
            return builder.equal(root.get("paymentType"), paymentType);
        };
    }

    private static Specification<Subscription> hasTrainer(Coach coach) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> {
            if (coach == null) {
                return null;
            }
            return builder.equal(root.get("coach"), coach);
        };
    }

    private static Specification<Subscription> createdByUser(User user) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> CommonSpecifications.createdByUSer(root, builder, user, "user");
    }


    private static Specification<Subscription> createdBetween(LocalDate startDate, LocalDate finishDate) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> CommonSpecifications.createdBetween(root, builder, startDate, finishDate, "createdAt");
    }

    private static Specification<Subscription> isExpire(Boolean isExpiredBool) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> CommonSpecifications.booleanStatus(root, builder, isExpiredBool, "isExpired");
    }

    private static Specification<Subscription> isFrozen(Boolean isFrozenBool) {
        return (Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder builder)
                -> CommonSpecifications.booleanStatus(root, builder, isFrozenBool, "isFrozen");
    }


    public static Specification<Subscription> getSpecs(Member member, PaymentType paymentType, Coach coach
            ,User user ,LocalDate startDate, LocalDate finishDate, Boolean isExpiredBool, Boolean isFrozenBool) {
        return Specification
                .where(hasMember(member))
                .and(hasPaymentType(paymentType))
                .and(hasTrainer(coach))
                .and(createdByUser(user))
                .and(createdBetween(startDate, finishDate))
                .and(isExpire(isExpiredBool))
                .and(isFrozen(isFrozenBool));
    }


}
