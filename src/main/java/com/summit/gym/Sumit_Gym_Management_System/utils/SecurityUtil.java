package com.summit.gym.Sumit_Gym_Management_System.utils;

import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

//TODO: Extract shift id from session

@Component
@RequiredArgsConstructor
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SecurityUtil {

    private final ShiftRepo shiftRepo;
    private final UserRepo userRepo;
    private final HttpSession httpSession;

    public Shift findCurrentShift() {



//        return new Shift();
        return shiftRepo.findById(1L).orElseThrow(EntityNotFoundException::new);
    }


    public Long extractUserIdFromSession() {
//        Object userIdObj = httpSession.getAttribute("userId");
//        if (userIdObj == null) {
//            throw new IllegalStateException("Couldn't find user for the session");
//        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Long userIdObj = user.getId();

        return userIdObj;
    }

}
