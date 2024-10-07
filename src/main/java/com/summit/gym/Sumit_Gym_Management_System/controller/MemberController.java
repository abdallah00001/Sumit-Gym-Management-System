package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.dto.MemberDto;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.service.MemberService;
import com.summit.gym.Sumit_Gym_Management_System.validation.annotations.Phone;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get ALL members")
    @GetMapping
    public List<MemberDto> findAllMembers() {
        return memberService.findAllMembers();
    }

    @Operation(summary = "Get member by id")
    @GetMapping("/{id}")
    public MemberDto findById(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @GetMapping("/phone/{phone}")
    public MemberDto findByPhone(@PathVariable
                                 @Phone
                                 String phone) {
        return memberService.findByPhone(phone);
    }


    @Operation(summary = "Save new member")
    @PostMapping
    public ResponseEntity<String> saveMember(@RequestBody @Valid Member member) {
        memberService.saveMember(member);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Member created Successfully");
    }


    @Operation(summary = "Delete a member by id")
    @DeleteMapping
    public ResponseEntity<String> deleteMemberById(Long id) {
        memberService.deleteMember(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Member deleted successfully");
    }

    @Operation(summary = "Updates a member",
            description = """
                    old member is fetched from db by id
                    then populated with [ALL] data from updated member even null values
                    so send full updated member plz""")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable Long id,
                                               @RequestBody Member updatedMember) {
        memberService.updateMember(id, updatedMember);
        return ResponseEntity
                .ok("Member updated successfully");
    }

//    @PostMapping("/attendance/{date}")
//    public ResponseEntity<String> addAttendanceDate(@PathVariable
//                                                    @PastOrPresent(message = "Date must be past or present")
//                                                    LocalDate date,
//                                                    @RequestBody Member member) {
//        memberService.addAttendedDay(member, date);
//        return ResponseEntity
//                .ok("Day added successfully");
//    }

//    @PostMapping("/sub")
//    public ResponseEntity<String> addSubscription(@RequestBody Subscription) {
//
//
//    }

}
