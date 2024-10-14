package com.summit.gym.Sumit_Gym_Management_System.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.*;

public class WhatsAppMessengerService {

//    private final static String JAR_LOCATION = "bot\\WhatsAppMessanger-1.0-SNAPSHOT.jar";
    private final static String JAR_LOCATION = "C:\\summit-gym\\bot\\WhatsAppMessanger-1.0-SNAPSHOT-shaded.jar";

/*    public static void sendCode(String phone, String codeLocation) {

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
    }*/


    private static final BlockingQueue<SeleniumTask> taskQueue = new LinkedBlockingQueue<>();
    private static final Semaphore semaphore = new Semaphore(1);
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();


    static {
        startQueueProcessor();
    }

    private static class SeleniumTask {
        String phone;
        String codeLocation;

        SeleniumTask(String phone, String codeLocation) {
            this.phone = phone;
            this.codeLocation = codeLocation;
        }
    }

    public static void sendCode(String phone, String codeLocation) {
        taskQueue.offer(new SeleniumTask(phone, codeLocation));
    }

    private static void startQueueProcessor() {
        executor.submit(() -> {
            while (true) {
                try {
                    SeleniumTask task = taskQueue.take();
                    semaphore.acquire();
                    try {
                        executeSeleniumJar(task.phone, task.codeLocation);
                    } finally {
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    private static void executeSeleniumJar(String phone, String codeLocation) {
        try {
            String[] command = {"java", "-jar", JAR_LOCATION, phone, codeLocation};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            System.out.println("Jar exited with code: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        sendCode(null, null);
    }


}
