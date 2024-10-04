package com.summit.gym.Sumit_Gym_Management_System.controller;

import com.summit.gym.Sumit_Gym_Management_System.dto.reports.ReportDto;
import com.summit.gym.Sumit_Gym_Management_System.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{startDate}/{finishDate}")
    public ReportDto generateGenericReport(@PathVariable LocalDate startDate,
                                           @PathVariable LocalDate finishDate) {
        return reportService.generateGeneralReport(startDate, finishDate);
    }

}
