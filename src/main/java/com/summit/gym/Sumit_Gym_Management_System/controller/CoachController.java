package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.model.Coach;
import com.summit.gym.Sumit_Gym_Management_System.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coach")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @GetMapping
    public List<Coach> findAllCoaches() {
        return coachService.findAllCoaches();
    }

    @PostMapping("/admin/save")
    public ResponseEntity<String> save(@RequestBody Coach coach) {
        coachService.save(coach);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Coach saved successfully");
    }



}
