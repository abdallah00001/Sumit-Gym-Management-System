package com.summit.gym.Sumit_Gym_Management_System.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationView {

    @GetMapping("/register")
    public String registrationView() {
        return "final/project-v2/reg.html";
    }

}
