package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/cashiers")
    public List<User> findAllCashiers() {
        return userService.findAllCashiers();
    }

    @GetMapping("/cashiers/{userName}")
    public User findCashierByUserName(@PathVariable String userName) {
        return userService.findByUserName(userName);
    }

    @GetMapping
    public User findAdmin() {
        return userService.findAdmin();
    }

    @PostMapping("/cashiers")
    public ResponseEntity<String> saveCashier(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Saved Successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateAdmin(User updatedAdmin) {
        User admin = userService.findAdmin();
        updatedAdmin.setId(admin.getId());
        return ResponseEntity
                .ok("User updated Successfully");
    }

    @PutMapping("/cashiers/{userName}")
    public ResponseEntity<String> updateNormalUser(@PathVariable String userName,
                                                   @RequestBody User updatedUser) {
        userService.updateNormalUser(userName, updatedUser);
        return ResponseEntity
                .ok("User updated Successfully");
    }



}
