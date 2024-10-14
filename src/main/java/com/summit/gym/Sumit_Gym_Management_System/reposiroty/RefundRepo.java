package com.summit.gym.Sumit_Gym_Management_System.reposiroty;

import com.summit.gym.Sumit_Gym_Management_System.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RefundRepo extends JpaRepository<Refund,Long> {

    @Query("SELECT COUNT(DISTINCT s.member) FROM Refund r " +
            "JOIN r.subscription s " +
            "WHERE DATE(r.createdAt) BETWEEN :start AND :finish")
    int countDistinctMembersThatRefundedByDateBetween(@Param("start") LocalDate start,
                                                      @Param("finish") LocalDate finish);

    @Query("SELECT COALESCE(SUM(r.moneyRefunded), 0) FROM Refund r " +
            "WHERE DATE(r.createdAt) BETWEEN :start AND :finish")
    long findTotalAmountRefundedByDateBetween(@Param("start") LocalDate start,
                                              @Param("finish") LocalDate finish);


}
