package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.ShiftDto;
import com.summit.gym.Sumit_Gym_Management_System.dto_mappers.ShiftDtoMapper;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.MemberNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.ShiftRepo;
import com.summit.gym.Sumit_Gym_Management_System.utils.SessionAttributesManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepo shiftRepo;
    private final SessionAttributesManager sessionAttributesManager;
    private final MemberRepo memberRepo;
    private final ShiftDtoMapper shiftDtoMapper;

    public List<ShiftDto> findAll() {
        return shiftRepo.findAll().stream().map(shiftDtoMapper::toShiftDto)
                .toList();
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
        Shift shift = sessionAttributesManager.getCurrentShift();
//        Shift shift = shiftRepo.findById(1L).orElseThrow();
//        System.out.println(subscription);
        Member member = memberRepo.findById(memberId).orElseThrow(MemberNotFoundException::new);
        subscription.setMember(member);
        member.addSubscription(subscription);
        shift.getSubscriptions().add(subscription);
        shiftRepo.save(shift);
    }

    public String closeShift(int counterMoney) {
        Shift shift = sessionAttributesManager.getCurrentShift();
        int totalMoney = shift.close();
        int diff = counterMoney - totalMoney;
        shiftRepo.save(shift);
        return String.format("""
                        Shift total: %d
                        You entered: %d
                        Difference: %d
                        """,
                totalMoney, counterMoney, diff);
    }

}
