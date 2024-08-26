package com.summit.gym.Sumit_Gym_Management_System.reposiroty;


import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepo extends JpaRepository<Shift, Long> {

    //All shifts related to user
    List<Shift> findByUser(User user);

    //All shifts related to start time
    List<Shift> findByStartDateTime(LocalDateTime start);

    Optional<Shift> findByStartDateTimeAndUser(LocalDateTime start, User user);

}
