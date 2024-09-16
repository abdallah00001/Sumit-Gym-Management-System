package com.summit.gym.Sumit_Gym_Management_System.listeners;

import com.summit.gym.Sumit_Gym_Management_System.exceptions.ActiveShiftReassignedException;
import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

//ADD shift and user id to sessions
// {{{NOTE}}}: if user has active shift it is reassigned and exception is thrown
// to notify the user
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final HttpSession httpSession;
    private final ShiftRepo shiftRepo;
    private final static String USER_ID_ATTRIBUTE = "userId";
    private final static String SHIFT_ID_ATTRIBUTE = "shiftId";
//    private final static String SHIFT_REASSIGNED_ATTRIBUTE = "shiftId";

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        User user = saveUserIdToSession(authentication);
        saveNewShiftAndAddIdToSession(user);
    }

    private User saveUserIdToSession(Authentication authentication) {
            User user = (User) authentication.getPrincipal();
            httpSession.setAttribute(USER_ID_ATTRIBUTE, user.getId());
//            System.out.println("/////////////////////");
//            System.out.println("User is: " + user.getUsername());
//            System.out.println("User id is: " + httpSession.getAttribute(USER_ID_ATTRIBUTE));
//            System.out.println("/////////////////////");
            return user;
//        }
    }

    private void saveNewShiftAndAddIdToSession(User user) {
        Shift shift;
        Optional<Shift> shiftOptional = shiftRepo.findFirstByUserOrderByStartDateTimeDesc(user);
//        boolean isReassigned = false;

        //If user already has active shift we re-assign it
        if (shiftOptional.isPresent() && shiftOptional.get().isActive()) {
            shift = shiftOptional.get();
//            isReassigned = true;
        } else {
            Shift tempShift = new Shift();
            tempShift.setUser(user);
            shift = shiftRepo.save(tempShift);
        }

        httpSession.setAttribute(SHIFT_ID_ATTRIBUTE, shift.getId());

//        System.out.println("/////////////////////");
//        System.out.println("Shift for user: " + user.getUsername());
//        System.out.println("Shift id is: " + httpSession.getAttribute(SHIFT_ID_ATTRIBUTE));
//        if (isReassigned) System.out.println("Shift is reassigned");;
//        System.out.println("/////////////////////");

//        if (isReassigned) throw new ActiveShiftReassignedException();
    }



}
