package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.reports.ReportDto;
import com.summit.gym.Sumit_Gym_Management_System.model.SubscriptionType;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.RefundRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final MemberRepo memberRepo;
    private final SubscriptionRepo subscriptionRepo;
    private final RefundRepo refundRepo;


    public ReportDto generateGeneralReport(LocalDate startDate, LocalDate finishDate) {
        ReportDto reportDto = new ReportDto();
        int membersAttendedCount = subscriptionRepo.countDistinctMembersByAttendance(startDate, finishDate);
        int membersThatPaidCount = subscriptionRepo.countDistinctMemberThatPaidByDateBetween(startDate, finishDate);
        long totalRevenue = subscriptionRepo.findTotalRevenueByDateBetween(startDate, finishDate);
        int membersThatRefundedCount = refundRepo.countDistinctMembersThatRefundedByDateBetween(startDate, finishDate);
        long totalAmountRefunded = refundRepo.findTotalAmountRefundedByDateBetween(startDate, finishDate);
        long membersThatRenewed = subscriptionRepo.countDistinctMembersThatRenewed(startDate, finishDate);
        List<Object[]> result = subscriptionRepo.countDistinctMembersBySubscriptionType(startDate, finishDate);
        Map<SubscriptionType, Long> membersPerTypeMap = formatSubTypeMembermap(result);


        reportDto.setMembersAttendedCount(membersAttendedCount);
        reportDto.setMemberThatPaidCount(membersThatPaidCount);
        reportDto.setTotalRevenue(totalRevenue);
        reportDto.setMembersThatRefundedCount(membersThatRefundedCount);
        reportDto.setTotalAmountRefunded(totalAmountRefunded);
        reportDto.setMembersThatRenewed(membersThatRenewed);
        reportDto.setMemberCountPerSubscriptionType(membersPerTypeMap);

        return reportDto;
    }


    private Map<SubscriptionType, Long> formatSubTypeMembermap(List<Object[]> resultList) {
        Map<SubscriptionType, Long> map = new HashMap<>();

        for (Object[] row : resultList) {
            SubscriptionType subscriptionType = (SubscriptionType) row[0];
            long numberOfMembers = (Long) row[1];
            map.put(subscriptionType, numberOfMembers);
        }
        return map;
    }

}
