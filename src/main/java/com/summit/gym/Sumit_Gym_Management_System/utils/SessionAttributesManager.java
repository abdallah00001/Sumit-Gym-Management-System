package com.summit.gym.Sumit_Gym_Management_System.utils;

import com.summit.gym.Sumit_Gym_Management_System.exceptions.UserNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

//TODO: Extract shift id from session

@Component
@RequiredArgsConstructor
//Session scoped to hold different sessions in final session field (STATEFUL)
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionAttributesManager {

    private final ShiftRepo shiftRepo;
    private final UserRepo userRepo;
    private final HttpSession httpSession;

    private final static String USER_ID_ATTRIBUTE = "userId";
    private final static String SHIFT_ID_ATTRIBUTE = "shiftId";


    private void setSessionAttribute(String attributeName, Object value) {
        httpSession.setAttribute(attributeName, value);
    }

    private Object getSessionAttribute(String attributeName) {
        return httpSession.getAttribute(attributeName);
    }


    public void setUserId(Long userId) {
        setSessionAttribute(USER_ID_ATTRIBUTE, userId);
    }

    public void setShiftId(Long shiftId) {
        setSessionAttribute(SHIFT_ID_ATTRIBUTE, shiftId);
    }

    public Shift getCurrentShift() {
        Long shiftId = (Long) getSessionAttribute(SHIFT_ID_ATTRIBUTE);
        return shiftRepo.findById(shiftId)
                .orElseThrow(() -> new  EntityNotFoundException("Shift was not found"));
    }

    public User getUser() {
        Long userId = (Long) getSessionAttribute(USER_ID_ATTRIBUTE);
        return userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public Long getUserId() {
        return (Long) getSessionAttribute(USER_ID_ATTRIBUTE);
    }

    public Long getShiftID() {
        return (Long) getSessionAttribute(SHIFT_ID_ATTRIBUTE);
    }

}
