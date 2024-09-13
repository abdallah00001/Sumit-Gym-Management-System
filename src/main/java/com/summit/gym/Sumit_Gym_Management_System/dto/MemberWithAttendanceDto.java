package com.summit.gym.Sumit_Gym_Management_System.dto;

import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberWithAttendanceDto {

    private Long id;
    private String name;
    private String phone;
    private List<LocalDate> attendedDays;
    private int remainingDaysCount;


//    public static MemberWithAttendanceDto mapToDto(Member member) {
//        List<LocalDate> attendance = new ArrayList<>();
//        int remaining = 0;
//        Subscription latestSubscription = member.getLatestSubscription();
//        if (latestSubscription != null) {
//            attendance = latestSubscription.getAttendedDays();
//            remaining = latestSubscription.getSubscriptionType().getDurationInDays() - attendance.size();
//        }
//        return new MemberWithAttendanceDto(member.getId(), member.getName(), member.getPhone(),
//                attendance, remaining);
//
//    }

    public static Converter<Member, MemberWithAttendanceDto> toDtoConverter = new Converter<Member, MemberWithAttendanceDto>() {
        @Override
        public MemberWithAttendanceDto convert(MappingContext<Member, MemberWithAttendanceDto> context) {
            Member member = context.getSource();
            List<LocalDate> attendance = new ArrayList<>();
            int remaining = 0;
            Subscription latestSubscription = member.getLatestSubscription();
            if (latestSubscription != null) {
                attendance = latestSubscription.getAttendedDays();
                remaining = latestSubscription.getSubscriptionType().getDurationInDays() - attendance.size();
            }
            return new MemberWithAttendanceDto(member.getId(), member.getName(), member.getPhone(),
                    attendance, remaining);
        }
    };

}
