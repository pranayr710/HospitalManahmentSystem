package com.hms.threads;

import com.hms.utils.DataLogger;

public class HospitalTask implements Runnable {
    private final String taskName;

    public HospitalTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        DataLogger.log("[TASK START] " + taskName);
        try {
            simulateWork();
        } catch (InterruptedException e) {
            DataLogger.error("Task interrupted: " + taskName);
        }
        DataLogger.log("[TASK COMPLETE] " + taskName);
    }

    private void simulateWork() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            Thread.sleep(500);
            DataLogger.log(taskName + " progress: " + ((i + 1) * 33) + "%");
        }
    }
}
