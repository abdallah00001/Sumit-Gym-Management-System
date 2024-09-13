//package com.summit.gym.Sumit_Gym_Management_System.utils;
//
//import com.summit.gym.Sumit_Gym_Management_System.model.User;
//import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
////TODO: Save shift Id to session
//
//@Component
//@RequiredArgsConstructor
//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
//public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
//
//    private final HttpSession httpSession;
//    private final ShiftRepo shiftRepo;
//    private final static String USER_ID_ATTRIBUTE = "userId";
//
//    @Override
//    public void onApplicationEvent(AuthenticationSuccessEvent event) {
//        Authentication authentication = event.getAuthentication();
//        saveUserIdToSession(authentication);
//
//    }
//
//    private void saveUserIdToSession(Authentication authentication) {
//        if (httpSession.getAttribute(USER_ID_ATTRIBUTE) == null) {
//            User user = (User) authentication.getPrincipal();
//            httpSession.setAttribute(USER_ID_ATTRIBUTE, user.getId());
//        }
//    }
//
//}
