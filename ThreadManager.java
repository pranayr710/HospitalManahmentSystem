package com.hms.threads;

public class ThreadManager {
    // Runs three tasks in parallel.
    public static void runInParallel(Runnable r1, Runnable r2, Runnable r3) {
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.err.println("One of the threads was interrupted.");
        }
    }

	public void runTasks() {
		// TODO Auto-generated method stub
		
	}
}
