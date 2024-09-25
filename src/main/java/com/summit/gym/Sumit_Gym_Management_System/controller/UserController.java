package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.dto.UserDto;
import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get ALl Cashier Users")
    @GetMapping("/cashiers")
    public List<UserDto> findAllCashiers() {
        return userService.findAllCashiers();
    }

    @Operation(summary = "Get ONLY cashier user by username",
            description = "only returns cashiers not admin even when searched with admin username")
    @GetMapping("/cashiers/{userName}")
    public UserDto findCashierByUserName(@PathVariable String userName) {
        return userService.findByUserName(userName);
    }

    @Operation(summary = "Get the one admin account in the system")
    @GetMapping("/admin")
    public UserDto findAdmin() {
        return userService.findAdmin();
    }

    @Operation(summary = "Save Only new cashiers")
    @PostMapping("/cashiers")
    public ResponseEntity<String> saveCashier(@RequestBody
//                                                  @Valid
                                                  @Validated
                                                  User user) {
        user.setRole(Role.ROLE_CASHIER);
        userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User Saved Successfully");
    }

    @Operation(summary = "Update the admin user",
            description = "Make sure updated admin HAS ALL REQUIRED FIElDS POPULATED")
    @PutMapping("/admin")
    public ResponseEntity<String> updateAdmin(@RequestBody @Valid User updatedAdmin) {
        UserDto admin = userService.findAdmin();
        userService.updateAdmin(updatedAdmin);
        return ResponseEntity
                .ok("User updated Successfully");
    }

    @Operation(summary = "update normal user like cashiers",
            description = "MAKE SURE USER IS FULLY POPULATED")
    @PutMapping("/cashiers/{userName}")
    public ResponseEntity<String> updateNormalUser(@PathVariable String userName,
                                                   @RequestBody @Valid User updatedUser) {
        userService.updateNormalUser(userName, updatedUser);
        return ResponseEntity
                .ok("User updated Successfully");
    }

    @Operation(summary = "Deletes cashier by username")
    @DeleteMapping("/cashiers/{userName}")
    public ResponseEntity<String> deleteCashier(@PathVariable String userName) {
        userService.deleteCashier(userName);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Cashier deleted successfully");
    }


}
