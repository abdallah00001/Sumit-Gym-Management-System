package com.summit.gym.Sumit_Gym_Management_System.dto_mappers;

import com.summit.gym.Sumit_Gym_Management_System.dto.MemberDto;
import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionDto;
import com.summit.gym.Sumit_Gym_Management_System.dto.SubscriptionForMemberDto;
import com.summit.gym.Sumit_Gym_Management_System.enums.Gender;
import com.summit.gym.Sumit_Gym_Management_System.model.Member;
import com.summit.gym.Sumit_Gym_Management_System.model.Subscription;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;


@Component
public class MemberMapper extends BaseMapper {


    private final SubscriptionMapper subscriptionMapper;

    public MemberMapper(ModelMapper modelMapper, SubscriptionMapper subscriptionMapper) {
        super(modelMapper);
        this.subscriptionMapper = subscriptionMapper;
        addModelMapperConverters(toDtoConverter());
//        addModelMapperConverters(toDtoConverter(), toMemberConverter());
    }

    public MemberDto mapToMemberDto(Member member) {
        return mapToDto(member, MemberDto.class);
    }

//    public Member mapToMember(MemberWithAttendanceDto dto) {
//        return mapToEntity(dto, Member.class);
//    }

    public Member updateMember(Member originalMember, Member updatedMember) {
        modelMapper.map(updatedMember, originalMember);
        return originalMember;
    }


    private Converter<Member, MemberDto> toDtoConverter() {
        return new Converter<Member, MemberDto>() {
            @Override
            public MemberDto convert(MappingContext<Member, MemberDto> context) {
                Member member = context.getSource();
                Subscription latestSubscription = member.getLatestSubscription();
                Subscription pendingSub = member.getPendingSubscription();
                Gender gender = member.getGender();

                SubscriptionDto subscriptionDto = latestSubscription != null ?
                        subscriptionMapper.toSubscriptionDto(latestSubscription) :
                        null;

                SubscriptionDto pendingSubDto = pendingSub != null ?
                        subscriptionMapper.toSubscriptionDto(pendingSub) :
                        null;

                return new MemberDto(member.getId(), member.getName(), member.getPhone(),
                        gender, member.getBirthDate(), subscriptionDto, pendingSubDto);
            }
        };
    }


//    private Converter<MemberWithAttendanceDto, Member> toMemberConverter() {
//        return new Converter<MemberWithAttendanceDto, Member>() {
//            @Override
//            public Member convert(MappingContext<MemberWithAttendanceDto, Member> context) {
//                MemberWithAttendanceDto memberWithAttendanceDto = context.getSource();
//                Subscription subscriptionEntity = subscriptionDtoMapper.toSubscriptionEntity(memberWithAttendanceDto.getLatestSubscriptionDto());
//                Member member = new Member();
//                member.setId(memberWithAttendanceDto.getId());
//                member.setPhone(memberWithAttendanceDto.getPhone());
//                member.setGender(memberWithAttendanceDto.getGender());
//                member.setLatestSubscription(subscriptionEntity);
//                return member;
//            }
//        };
//    }

}
