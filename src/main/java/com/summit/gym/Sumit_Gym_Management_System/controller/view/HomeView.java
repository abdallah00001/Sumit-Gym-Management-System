package com.summit.gym.Sumit_Gym_Management_System.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeView {

    @GetMapping("/")
    public String dashboard() {
        return "dashboard.html";
    }

//    @GetMapping("/favicon.ico")
//    public String favicon() {
//        return "favicon.ico";
//    }

}
