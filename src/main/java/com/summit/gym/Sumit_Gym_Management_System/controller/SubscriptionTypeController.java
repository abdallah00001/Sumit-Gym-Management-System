package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import com.summit.gym.Sumit_Gym_Management_System.service.SubscriptionTypeService;
import com.summit.gym.Sumit_Gym_Management_System.validation.validators.SubscriptionTypeValidator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

@RestController
@RequestMapping("/api/admin/sub-type")
@RequiredArgsConstructor
public class SubscriptionTypeController {

    private final SubscriptionTypeService subscriptionTypeService;
    private final SubscriptionTypeValidator validator;

    @Operation(summary = "Get all subscription types",
            description = "User for subscription type drop down menu ")
    @GetMapping
    public List<SubscriptionType> getAll() {
        return subscriptionTypeService.findAll();
    }

    @Operation(summary = "Save new type")
    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid SubscriptionType type) {

        subscriptionTypeService.save(type);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Type saved successfully");
    }

    @Operation(summary = "Delete a type")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteType(@PathVariable Long id) {
        subscriptionTypeService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Deleted successfully");
    }


}
