package com.summit.gym.Sumit_Gym_Management_System.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class WhatsAppMessengerService {

    private final static String JAR_LOCATION = "I:\\java-projects\\WhatsAppMessenger\\target\\WhatsAppMessanger-1.0-SNAPSHOT.jar";

    public static void sendCode(String phone, String codeLocation) {

        new Thread(() -> {
            try {
                String[] command = {
                        "java",
                        "-jar",
                        JAR_LOCATION,
                        phone,
                        codeLocation // Add as many args as needed
                };

                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);

                // Start the process
                Process process = processBuilder.start();

//            // Capture and print output
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
//            }

//            int exitCode = process.waitFor();
//            System.out.println("Jar exited with code: " + exitCode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public static void main(String[] args) {
        sendCode(null, null);
    }


}
