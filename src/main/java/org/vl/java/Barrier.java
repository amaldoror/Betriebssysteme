package org.vl.java;

public class Barrier {
    private final int numberOfThreads;
    private int count;
    private int generation;
    private final Runnable barrierAction;


    public Barrier(int numberOfThreads, Runnable barrierAction) {
        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be greater than 0");
        }
        this.numberOfThreads = numberOfThreads;
        this.count = 0;
        this.generation = 0;
        this.barrierAction = barrierAction;
    }

    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        count++;

        if (count == numberOfThreads) {
            count = 0;
            generation++;
            if (barrierAction != null) {
                barrierAction.run();
            }
            notifyAll();
        } else {
            while (arrivalGeneration == generation) {
                wait();
            }
        }
    }
}
