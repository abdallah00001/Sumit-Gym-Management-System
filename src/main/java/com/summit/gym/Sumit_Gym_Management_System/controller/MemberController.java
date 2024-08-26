package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Shift;
import com.summit.gym.Sumit_Gym_Management_System.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public List<Member> findAllMembers() {
        return memberService.findAllMembers();
    }

    @GetMapping("/{id}")
    public Member findById(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @PostMapping
    public ResponseEntity<String> saveMember(@RequestBody @Valid Member member) {
        memberService.saveMember(member);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Member created Successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMemberById(Long id) {
        memberService.deleteMember(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Member deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable Long id,
                                               @RequestBody Member updatedMember) {
        memberService.updateMember(id, updatedMember);
        return ResponseEntity
                .ok("Member updated successfully");
    }


}
