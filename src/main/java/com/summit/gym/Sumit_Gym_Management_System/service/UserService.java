package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.UserNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;


    public List<User> findAllCashiers() {
        return userRepo.findByRole(Role.ROLE_CASHIER);
    }

    public User findCashierByUserName(String userName) {
        return userRepo.findCashierByUserName(userName)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findAdmin() {
        return userRepo.findAdmin()
                .orElseThrow(UserNotFoundException::new);
    }


    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName)
                .orElseThrow(UserNotFoundException::new);
    }


    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateNormalUser(String oldUserName, User updatedUser) {
        User oldUser = findByUserName(oldUserName);
        updatedUser.setId(oldUser.getId());
        userRepo.save(updatedUser);
    }

    public void updateAdmin(User updatedAdmin) {
        if (!updatedAdmin.getRole().equals(Role.ROLE_ADMIN)) {
            throw new IllegalArgumentException("Admin account must have ROLE_ADMIN");
        }
        User admin = findAdmin();
        updatedAdmin.setId(admin.getId());
        userRepo.save(updatedAdmin);
    }

    public void deleteCashier(String userName) {
        User userToDelete = findCashierByUserName(userName);
        userRepo.delete(userToDelete);
    }

}
