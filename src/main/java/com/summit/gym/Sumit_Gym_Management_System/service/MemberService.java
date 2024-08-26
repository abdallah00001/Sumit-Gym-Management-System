package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.exceptions.UserNotFoundException;
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

    public List<Member> findAllMembers() {
        return memberRepo.findAll();
    }

    public Member findMemberById(Long id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member was not found"));
    }

    public void saveMember(Member member) {
        memberRepo.save(member);
    }

    public void deleteMember(Long id) {
        Member member = findMemberById(id);
        memberRepo.delete(member);
    }


    public void updateMember(Long id, Member updatedMember) {
        if (!memberRepo.existsById(id)) {
            throw new UserNotFoundException("User you are trying to update " +
                    "does not exist");
        }
        updatedMember.setId(id);
        memberRepo.save(updatedMember);
    }


}
