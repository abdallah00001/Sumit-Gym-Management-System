package com.summit.gym.Sumit_Gym_Management_System.Config;

import com.summit.gym.Sumit_Gym_Management_System.model.User;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username doesn't exist"));

//        String username2 = user.getUsername();
//        String password =  user.getPassword();
//        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//        return new org.springframework.security.core.userdetails.User(username2,password,authorities);
        return user;
    }
}
