package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.UserDto;
import com.summit.gym.Sumit_Gym_Management_System.dto_mappers.UserMapper;
import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.UserNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;


    private void encryptPassAndSaveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }


    public List<UserDto> findAllCashiers() {
        return userRepo.findByRole(Role.ROLE_CASHIER)
                .stream().map(userMapper::mapToUserDto)
                .toList();
    }

    public User findCashierByUserName(String userName) {
        return userRepo.findCashierByUserName(userName)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDto findAdmin() {
        User admin = userRepo.findAdmin()
                .orElseThrow(UserNotFoundException::new);
        return userMapper.mapToUserDto(admin);
    }


    public UserDto findByUserName(String userName) {
        User user = userRepo.findByUserName(userName)
                .orElseThrow(UserNotFoundException::new);
        return userMapper.mapToUserDto(user);
    }


    public void saveUser(User user) {
//        if (userRepo.existsByUserName(user.getUsername())) {
//            throw new IllegalArgumentException("Username already taken");
//        }


        encryptPassAndSaveUser(user);
    }

    public void updateNormalUser(String oldUserName, User updatedUser) {
        if (!updatedUser.getUsername().equals(oldUserName)
                && userRepo.existsByUserName(updatedUser.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        User oldUser = userRepo.findByUserName(oldUserName)
                .orElseThrow(UserNotFoundException::new);
        if (oldUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new IllegalArgumentException("Unauthorized to update super user");
        }
        updatedUser.setId(oldUser.getId());
        updatedUser.setRole(oldUser.getRole());
        encryptPassAndSaveUser(updatedUser);
    }

    public void updateAdmin(User updatedAdmin) {
//        if (!updatedAdmin.getRole().equals(Role.ROLE_ADMIN)) {
//            throw new IllegalArgumentException("Admin account must have ROLE_ADMIN");
//        }

        User admin = userRepo.findAdmin()
                .orElseThrow(UserNotFoundException::new);

        if (!admin.getUsername().equals(updatedAdmin.getUsername())
                && userRepo.existsByUserName(updatedAdmin.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        updatedAdmin.setId(admin.getId());
        updatedAdmin.setRole(Role.ROLE_ADMIN);
        encryptPassAndSaveUser(updatedAdmin);
    }

    public void deleteCashier(String userName) {
        User userToDelete = findCashierByUserName(userName);
        userRepo.delete(userToDelete);
    }



}
