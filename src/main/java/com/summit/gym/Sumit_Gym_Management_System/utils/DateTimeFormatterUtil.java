package com.summit.gym.Sumit_Gym_Management_System.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeFormatterUtil {

    public static String periodToString(Period period) {
        if (period == null) {
            return "";
        }

        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        StringBuilder periodString = new StringBuilder();
        if (years > 0) {
            periodString.append(years).append(years == 1 ? " year, " : " years, ");
        }
        if (months > 0) {
            periodString.append(months).append(months == 1 ? " month, " : " months, ");
        }
        if (days > 0) {
            periodString.append(days).append(days == 1 ? " day" : " days");
        } else if (days == 0 && months == 0 &&  years == 0) {
            periodString.append("0 days");
        }

        // Trim any trailing comma and space
        return periodString.toString().replaceAll(", $", "");
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        String pattern = "yyyy-MM-dd hh:mm a";
        if (dateTime == null) {
            return ""; // Return empty string for null inputs
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return dateTime.format(formatter); // Return formatted date string
        } catch (DateTimeParseException e) {
            // Handle invalid patterns or formatting errors
            System.err.println("Invalid date format pattern: " + e.getMessage());
            return ""; // Return empty string if there's an error
        }
    }

    public static String formatLocalDate(LocalDate date) {
        String pattern = "dd/MM/yyyy";
        if (date == null) {
            return ""; // Return empty string for null inputs
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return date.format(formatter); // Return formatted date string
        } catch (DateTimeParseException e) {
            // Handle invalid patterns or formatting errors
            System.err.println("Invalid date format pattern: " + e.getMessage());
            return ""; // Return empty string if there's an error
        }
    }

    public static void main(String[] args) {
        System.out.println(formatLocalDateTime(LocalDateTime.now()));
        System.out.println(formatLocalDate(LocalDate.now()));
    }



}
