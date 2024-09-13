package com.summit.gym.Sumit_Gym_Management_System.reposiroty;

import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription,Long> {

    @Query("SELECT s FROM Subscription s WHERE DATE(s.createdAt) BETWEEN :startDate AND :finishDate")
    List<Subscription> findByCreatedAtBetween(@Param("startDate") LocalDate startDate,
                                              @Param("finishDate") LocalDate finish);

    Optional<Subscription> findLastByMemberOrderByCreatedAtAsc(Member member);

    List<Subscription> findByMember(Member member);

    @Query("SELECT s FROM Subscription s WHERE s.user.userName = :userName")
    List<Subscription> findByUserName(@Param("userName") String userName);

}
