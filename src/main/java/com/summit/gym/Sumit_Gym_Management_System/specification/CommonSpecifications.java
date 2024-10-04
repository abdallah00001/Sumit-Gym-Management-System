package com.summit.gym.Sumit_Gym_Management_System.specification;


import com.summit.gym.Sumit_Gym_Management_System.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;


public class CommonSpecifications {


    public static <T> Predicate hasNameLike(Root<T> root, CriteriaBuilder builder,
                                            String searchName, String fieldName) {
        if (searchName == null || searchName.isEmpty()) {
            return null;
        }
        return builder.like(root.get(fieldName), "%" + searchName + "%");
    }

    public static <T> Predicate hasNameEqualTO(Root<T> root, CriteriaBuilder builder,
                                               String searchName, String fieldName) {
        if (searchName == null || searchName.isEmpty()) {
            return null;
        }
        return builder.equal(root.get(fieldName), searchName);
    }

    public static <T> Predicate createdByUSer(Root<T> root, CriteriaBuilder builder,
                                              User user, String fieldName) {
        if (user == null) {
            return null;
        }
        return builder.equal(root.get(fieldName), user);
    }

    public static <T> Predicate createdAt(Root<T> root, CriteriaBuilder builder,
                                          LocalDate createDate, String fieldName) {
        if (createDate == null) {
            return null;
        }
        return builder.equal(
                builder.function("DATE", LocalDate.class, root.get(fieldName))
                , createDate
        );
    }

    public static <T> Predicate createdBetween(Root<T> root, CriteriaBuilder builder,
                                               LocalDate start, LocalDate finish, String fieldName) {
        if (start == null || finish == null) {
            return null;
        }
        if (start.isBefore(finish)) {
            throw new IllegalArgumentException(
                    "Finish date can't be before start date"
            );
        }
        return builder.between(
                builder.function("DATE", LocalDate.class, root.get(fieldName)),
                start, finish
        );
    }

    //For any status represented by boolean such as isActive isExpired isFrozen
    public static <T> Predicate booleanStatus(Root<T> root, CriteriaBuilder builder,
                                              Boolean active, String fieldName) {
        if (active == null) {
            return null;
        }
        return builder.equal(root.get(fieldName), active);
    }


}
