package com.company;

import com.hms.threads.ThreadManager;
import com.hms.utils.DataLogger;

public class EnhancedMain {
    public static void main(String[] args) {
        DataLogger.log("Launching Hospital Management System...");

        ThreadManager manager = new ThreadManager();
        manager.runTasks();

        DataLogger.log("Hospital Management System operations completed.");
    }
}
