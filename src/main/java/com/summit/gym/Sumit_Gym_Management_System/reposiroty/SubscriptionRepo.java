package com.summit.gym.Sumit_Gym_Management_System.reposiroty;

import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long>, JpaSpecificationExecutor<Subscription> {

    @Query("SELECT s FROM Subscription s WHERE DATE(s.createdAt) BETWEEN :startDate AND :finishDate")
    List<Subscription> findByCreatedAtBetween(@Param("startDate") LocalDate startDate,
                                              @Param("finishDate") LocalDate finish);

    Optional<Subscription> findLastByMemberOrderByCreatedAtAsc(Member member);

    List<Subscription> findByMember(Member member);

    @Query("SELECT s FROM Subscription s WHERE s.user.userName = :userName")
    List<Subscription> findByUserName(@Param("userName") String userName);

    //Select subs that are ACTIVE at the search start (even if expired mid-search period no problem)
    //Then from those sub check the attendance for ANY 1 DAY with in the search date range
    @Query("SELECT COUNT(DISTINCT s.member) FROM Subscription s " +
            "WHERE s.startDate <= :endDate " +  // Subscription must have started before or on the end date
            "AND s.expireDate >= :startDate " +    // Subscription must still be valid after or on the start date
            "AND EXISTS ( " +
            "  SELECT 1 FROM s.attendedDays a " +          // If 1 date is within range search stops
            "  WHERE a BETWEEN :startDate AND :endDate " + // Check attendance days within the specified range
            ")")
    int countDistinctMembersByAttendance(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);


    //Select count of members that were involved in payment operations (subscribed or renewed)
    @Query("SELECT COUNT(DISTINCT s.member) FROM Subscription s " +
            "WHERE s.startDate Between :start AND :finish")
    int countDistinctMemberThatPaidByDateBetween(@Param("start") LocalDate start,
                                                 @Param("finish") LocalDate finish);


/*    @Query("SELECT SUM(s.finalPrice) FROM Subscription s " +
            "WHERE s.startDate BETWEEN :start AND :finish")
    long findTotalRevenueByDateBetween(@Param("start") LocalDate start,
                                       @Param("finish") LocalDate finish);*/



}
