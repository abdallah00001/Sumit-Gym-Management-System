package com.summit.gym.Sumit_Gym_Management_System.listeners;

import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LogOutSuccessListener implements ApplicationListener<LogoutSuccessEvent> {

    private final ShiftRepo shiftRepo;

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        User user = (User) event.getAuthentication().getPrincipal();
        Optional<Shift> activeShiftOptional = shiftRepo.findFirstByUserOrderByStartDateTimeDesc(user);

        if (activeShiftOptional.isEmpty()) {
            throw new IllegalArgumentException(
                    "Shift was already closed"
            );
        }

        Shift shift = activeShiftOptional.get();
        shift.close();
        shiftRepo.save(shift);
    }
}
