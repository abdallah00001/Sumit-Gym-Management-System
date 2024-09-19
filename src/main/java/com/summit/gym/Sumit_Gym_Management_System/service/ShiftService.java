package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.exceptions.MemberNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
import com.summit.gym.Sumit_Gym_Management_System.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepo shiftRepo;
    private final SecurityUtil securityUtil;
    private final MemberRepo memberRepo;

    public List<Shift> findAll() {
        return shiftRepo.findAll();
    }

    public void startNewShift(Shift shift) {
        shiftRepo.save(shift);
    }

//    @Transactional
//    public void addSubscription(Subscription subscription) {
////        Shift shift = authenticationUtil.findCurrentShift();
//        Shift shift = shiftRepo.findById(1L).orElseThrow();
//        System.out.println(subscription);
//        shift.getSubscriptions().add(subscription);
//        shiftRepo.save(shift);
//    }

    @Transactional
    public void addSubscription(Subscription subscription, Long memberId) {
        Shift shift = securityUtil.findCurrentShift();
//        Shift shift = shiftRepo.findById(1L).orElseThrow();
//        System.out.println(subscription);
        Member member = memberRepo.findById(memberId).orElseThrow(MemberNotFoundException::new);
        subscription.setMember(member);
        member.getSubscriptions().add(subscription);
        shift.getSubscriptions().add(subscription);
        shiftRepo.save(shift);
    }

}
