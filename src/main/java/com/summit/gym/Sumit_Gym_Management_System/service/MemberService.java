package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.MemberDto;
import com.summit.gym.Sumit_Gym_Management_System.dto_mappers.MemberMapper;
import com.summit.gym.Sumit_Gym_Management_System.exceptions.MemberNotFoundException;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepo;
    private final MemberMapper memberMapper;

    public List<MemberDto> findAllMembers() {
        return memberRepo.findAll()
                .stream()
                .map(memberMapper::mapToMemberDto)
                .toList();
    }

    public MemberDto findMemberById(Long id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        return memberMapper.mapToMemberDto(member);
    }

    public void saveMember(Member member) {
        memberRepo.save(member);
    }

    public void deleteMember(Long id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        memberRepo.delete(member);
    }


    //Updating manually because DTOs supplied to front end lack some info
    //And front end cant provide fields like subscription
    public void updateMember(Long id, Member updatedMember) {
        Member oldMember = memberRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Member you are trying to update does not exist"));

        Member memberToSave = memberMapper.updateMember(oldMember, updatedMember);
        memberRepo.save(memberToSave);
    }


}
