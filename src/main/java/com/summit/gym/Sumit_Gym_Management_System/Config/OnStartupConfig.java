package com.summit.gym.Sumit_Gym_Management_System.Config;

import com.summit.gym.Sumit_Gym_Management_System.enums.Role;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnStartupConfig implements ApplicationRunner {

    private final UserRepo userRepo;

    @Override
    public void run(ApplicationArguments args) {
        createAndSaveDefaultAdmin();
    }



    private void createAndSaveDefaultAdmin() {
        if (userRepo.findAdmin().isEmpty()) {
            // Create a default admin
            User admin = new User();
            admin.setUserName("admin");
            admin.setPassword("adminpassword");
//            admin.setPassword(new BCryptPasswordEncoder().encode("adminpassword")); // Replace with a secure password
            admin.setRole(Role.ROLE_ADMIN);
            userRepo.save(admin);
        }
    }


}
