package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class FizzBuzz {
    private final int n;
    private final BlockingQueue<Integer> numberQueue = new ArrayBlockingQueue<>(1);
    private final BlockingQueue<String> outputQueue = new ArrayBlockingQueue<>(1);
    private final Semaphore sem = new Semaphore(1);

    public FizzBuzz(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzz fizzBuzz = new FizzBuzz(n);
        fizzBuzz.start();
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(this::fizz);
        executor.submit(this::buzz);
        executor.submit(this::fizzbuzz);
        executor.submit(this::number);

        for (int i = 1; i <= n; i++) {
            try {
                numberQueue.put(i);
                sem.acquire();
                System.out.print(outputQueue.take());
                if (i < n) {
                    System.out.print(", ");
                }
                sem.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        executor.shutdown();
    }

    private void fizz() {
        try {
            while (true) {
                int i = numberQueue.take();
                if (i % 3 == 0 && i % 5 != 0) {
                    outputQueue.put("fizz");
                } else {
                    numberQueue.put(i);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void buzz() {
        try {
            while (true) {
                int i = numberQueue.take();
                if (i % 5 == 0 && i % 3 != 0) {
                    outputQueue.put("buzz");
                } else {
                    numberQueue.put(i);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void fizzbuzz() {
        try {
            while (true) {
                int i = numberQueue.take();
                if (i % 3 == 0 && i % 5 == 0) {
                    outputQueue.put("fizzbuzz");
                } else {
                    numberQueue.put(i);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void number() {
        try {
            while (true) {
                int i = numberQueue.take();
                if (i % 3 != 0 && i % 5 != 0) {
                    outputQueue.put(String.valueOf(i));
                } else {
                    numberQueue.put(i);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}