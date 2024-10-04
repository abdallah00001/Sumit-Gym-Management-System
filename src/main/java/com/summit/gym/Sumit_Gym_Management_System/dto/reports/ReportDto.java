package com.summit.gym.Sumit_Gym_Management_System.dto.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReportDto {

    //Number of members that attended sessions
    private int membersAttendedCount;
    //Number of members that paid to renew or sub for the 1st time
    private int memberThatPaidCount;
    private int membersThatRefundedCount;
    private long totalRevenue;
    private long totalAmountRefunded;
}
