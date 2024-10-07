package com.summit.gym.Sumit_Gym_Management_System.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.summit.gym.Sumit_Gym_Management_System.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDto extends BaseDto{

    private Long id;
    private String name;
    private String phone;
    private Gender gender;
    private LocalDate birthDate;
    //    private SubscriptionForMemberDto subscriptionForMemberDto;
    @JsonIgnoreProperties("member")
    private SubscriptionDto subscriptionDto;

//    public static Converter<Member, MemberWithAttendanceDto> toDtoConverter() {
//        return new Converter<Member, MemberWithAttendanceDto>() {
//            @Override
//            public MemberWithAttendanceDto convert(MappingContext<Member, MemberWithAttendanceDto> context) {
//                Member member = context.getSource();
//                List<LocalDate> attendance = new ArrayList<>();
//                int remaining = 0;
//                Subscription latestSubscription = member.getLatestSubscription();
//                if (latestSubscription != null) {
//                    attendance = latestSubscription.getAttendedDays();
//                    remaining = latestSubscription.getSubscriptionType().getDurationInDays() - attendance.size();
//                }
//                return new MemberWithAttendanceDto(member.getId(), member.getName(), member.getPhone(),
//                        attendance, remaining, latestSubscription);
//            }
//        };
//    }


//    public static Converter<MemberWithAttendanceDto, Member> toMemberConverter = new Converter<MemberWithAttendanceDto, Member>() {
//        @Override
//        public Member convert(MappingContext<MemberWithAttendanceDto, Member> context) {
//            MemberWithAttendanceDto dto = context.getSource();
//            Member member =
//        }
//    };

}
