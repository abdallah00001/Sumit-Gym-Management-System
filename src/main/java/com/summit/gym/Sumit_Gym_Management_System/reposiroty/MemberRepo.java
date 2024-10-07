package com.summit.gym.Sumit_Gym_Management_System.reposiroty;

import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {
    Optional<Member> findByPhone(String phone);
}
