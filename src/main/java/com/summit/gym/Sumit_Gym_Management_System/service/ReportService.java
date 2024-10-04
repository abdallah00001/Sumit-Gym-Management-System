package com.summit.gym.Sumit_Gym_Management_System.service;

import com.summit.gym.Sumit_Gym_Management_System.dto.reports.ReportDto;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.MemberRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.RefundRepo;
import com.summit.gym.Sumit_Gym_Management_System.reposiroty.SubscriptionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

        reportDto.setMembersAttendedCount(membersAttendedCount);
        reportDto.setMemberThatPaidCount(membersThatPaidCount);
        reportDto.setTotalRevenue(totalRevenue);
        reportDto.setMembersThatRefundedCount(membersThatRefundedCount);
        reportDto.setTotalAmountRefunded(totalAmountRefunded);
        return reportDto;
    }



}
