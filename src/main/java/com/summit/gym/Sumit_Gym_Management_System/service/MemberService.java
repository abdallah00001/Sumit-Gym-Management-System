package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.MemberWithAttendanceDto;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.MemberNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepo;
    private final ModelMapper modelMapper;

    public List<MemberWithAttendanceDto> findAllMembers() {
        return memberRepo.findAll()
                .stream()
                .map(member ->modelMapper.map(member, MemberWithAttendanceDto.class))
                .toList();
    }

    public MemberWithAttendanceDto findMemberById(Long id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        return modelMapper.map(member, MemberWithAttendanceDto.class);
    }

    public void saveMember(Member member) {
        memberRepo.save(member);
    }

    public void deleteMember(Long id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        memberRepo.delete(member);
    }


    public void updateMember(Long id, Member updatedMember) {
        if (!memberRepo.existsById(id)) {
            throw new EntityNotFoundException("Member you are trying to update " +
                    "does not exist");
        }
        updatedMember.setId(id);
        memberRepo.save(updatedMember);
    }



}
