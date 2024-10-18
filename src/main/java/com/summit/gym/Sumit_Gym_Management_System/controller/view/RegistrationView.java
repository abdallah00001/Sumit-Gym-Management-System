package com.summit.gym.Sumit_Gym_Management_System.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationView {

    private final static String BASE_LOCATION = "production-front";

    @GetMapping("/register")
    public String registrationView() {
        return "final/project-v2/reg.html";
    }

//    @GetMapping("/register2/client")
    @GetMapping("/register2/client")
    public String registrationClientView() {
//        return BASE_LOCATION + "/v2/reg.html";
        return "redirect:/production-front/v2/reg.html";
    }


    @GetMapping("/register2/admin")
    public String registrationAdminView() {
        return BASE_LOCATION + "/v2/reg2.html";
    }
}
