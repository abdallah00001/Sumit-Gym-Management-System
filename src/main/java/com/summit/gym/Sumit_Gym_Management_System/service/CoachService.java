package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.model.Coach;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.CoachRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final static String NOT_FOUND_MSG = "Coach was not found";
    private final CoachRepo coachRepo;


    public List<Coach> findAllCoaches() {
        return coachRepo.findAll();
    }

    public Coach findCoachById(Long id) {
        return coachRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MSG));
    }

    public void save(Coach coach) {
        coachRepo.save(coach);
    }

    public void deleteById(Long id) {
        Coach coach = findCoachById(id);
        coachRepo.delete(coach);
    }


}
