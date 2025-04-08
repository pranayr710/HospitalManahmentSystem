package com.hms.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataLogger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        System.out.println("[LOG - " + LocalDateTime.now().format(formatter) + "]: " + message);
    }

    public static void error(String message) {
        System.err.println("[ERROR - " + LocalDateTime.now().format(formatter) + "]: " + message);
    }

    public static void logUserActivity(String user, String action) {
        log("User " + user + " performed action: " + action);
    }

    public static void logSystemCheck() {
        log("System check completed at " + LocalDateTime.now().format(formatter));
    }

    public static void logShutdown() {
        log("System is shutting down gracefully.");
    }

    public static void logStartup() {
        log("System startup completed.");
    }

    public static void notifyAdmin(String issue) {
        System.out.println("[ADMIN ALERT] " + issue);
    }
}
