package org.aufg2.podrace_alt;

import java.util.Random;

public class PodRacer {
    public static final int NUM_PODS = 8;
    public static final int NUM_ROUNDS = 3;
    public static boolean[] isFinished = new boolean[NUM_PODS];
    private static volatile boolean accidentOccurred = false;

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_PODS];
        boolean raceFinished = false;
        boolean accidentOccurred = false;

        // Create Pods & Start Pod threads
        for (int i = 0; i < NUM_PODS; i++) {
            Pod pod = new Pod(i);
            threads[i] = new Thread(pod);
            threads[i].start();
        }

        // Start accident thread
        Thread accidentThread = new Thread(() -> {
            try {
                int accidentTime = new Random().nextInt(1000);
                Thread.sleep(accidentTime);
                //System.out.println("Accident occurred after " + accidentTime + " ms");
                setAccidentOccurred(true);
            } catch (InterruptedException e) {
                System.err.println("PodRacer interrupted");
            }
        });
        accidentThread.start();


        // Check if race is finished or accident occurred
        while (!raceFinished && !accidentOccurred) {
            // Check if all pods have finished the race
            raceFinished = true;
            for (boolean finished : isFinished) {
                if (!finished) {
                    raceFinished = false;
                    break;
                }
            }

            // Check if accident occurred
            accidentOccurred = isAccidentOccurred();

        }

        /*
        // Interrupt all pod threads
        for (Thread thread : threads) {
            thread.interrupt();
        }
        */

        /*
        // Wait for accident thread to finish
        try {
            accidentThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
    }

    public static synchronized boolean isAccidentOccurred() {
        return accidentOccurred;
    }

    public static synchronized void setAccidentOccurred(boolean value) {
        accidentOccurred = value;
    }
}
