package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        Runnable displayElapsedTimeTask = new Runnable() {
            private long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println("Час, що минув: " + elapsedTime + " секунд");
            }
        };
        Runnable displayFiveSecondsMessageTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("Минуло 5 секунд");
            }
        };
        scheduler.scheduleAtFixedRate(displayElapsedTimeTask, 0, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(displayFiveSecondsMessageTask, 5, 5, TimeUnit.SECONDS);
    }
}