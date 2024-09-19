package com.summit.gym.Sumit_Gym_Management_System.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MembersView {

    @GetMapping("/members")
    public String members() {
        return "members.html";
    }

    @GetMapping("/1member")
    public String member() {
        return "member.html";
    }

}
