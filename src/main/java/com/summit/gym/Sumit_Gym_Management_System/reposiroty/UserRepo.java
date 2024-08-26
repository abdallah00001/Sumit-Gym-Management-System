package com.summit.gym.Sumit_Gym_Management_System.reposiroty;

import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    //To fetch list of cashiers
    List<User> findByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.role = ROLE_ADMIN")
    Optional<User> findAdmin();

    @Query("SELECT u FROM User u WHERE u.role = ROLE_CASHIER AND u.userName = :userName")
    Optional<User> findCashierByUserName(String userName);

}
