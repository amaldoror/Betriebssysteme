package org.aufg2.podrace_alt;

import java.util.Random;


public class Pod implements Runnable {

    // A random number generator used to simulate the time it takes for a Pod to complete a round
    private static final Random random = new Random();

    // An array to store the total time taken by each Pod to complete the race
    private static int[] times = new int[PodRacer.NUM_PODS];

    // An array to store the final position of each Pod in the race
    private static int[] positions = new int[PodRacer.NUM_PODS];

    // An array to keep track of whether each Pod has finished the race
    private static boolean[] isFinishedRound = new boolean[PodRacer.NUM_PODS];

    // The ID of this Pod
    private final int podID;

    // The total time taken by this Pod to complete the race
    private int timeTotal;

    // Constructor to initialize the Pod with its ID
    public Pod(int podID) {
        this.podID = podID;
        this.timeTotal = 0;
    }

    // This method is called when the Pod starts the race
    @Override
    public void run() {

        // The Pod completes the specified number of rounds
        for (int round = 0; round < PodRacer.NUM_ROUNDS; round++) {
            // The time taken by the Pod to complete this round is randomly generated
            int timeRound = random.nextInt(100);

            try {
                // The Pod sleeps for the time taken to complete this round
                Thread.sleep(timeRound);
            } catch (InterruptedException e) {
                // If the Pod is interrupted, it checks if an accident has occurred
                if (PodRacer.isAccidentOccurred()) {
                    // If an accident has occurred, the Pod returns immediately
                    return;
                } else {
                    // If the interruption is not due to an accident, it is re-thrown as a RuntimeException
                    throw new RuntimeException(e);
                }
            }

            // If an accident has occurred, the Pod returns immediately
            if (PodRacer.isAccidentOccurred()) {
                System.out.println("Accident. Race finished. Pod ID: " + podID);
                return;
            }

            // The time taken to complete this round is added to the total time
            timeTotal += timeRound;
        }

        // After completing all rounds, the Pod updates its position and checks if it is the last Pod to finish
        synchronized (Pod.class) {
            times[podID] = timeTotal;
            isFinishedRound[podID] = true;
            int position = 1;
            for (int i = 0; i < PodRacer.NUM_PODS; i++) {
                if (times[i] < timeTotal && isFinishedRound[i]) {
                    position++;
                }
            }
            positions[podID] = position;
            if (position == PodRacer.NUM_PODS) {
                printResults();
            }
        }
    }

    // This method is called when the last Pod finishes the race to print the results
    private void printResults() {
        System.out.println("**** Endstand ****");
        for (int pos = 1; pos <= PodRacer.NUM_PODS; pos++) {
            for (int i = 0; i < PodRacer.NUM_PODS; i++) {
                if (positions[i] == pos) {
                    System.out.println(pos + ". Platz: Pod " + i + " Zeit: " + times[i]);
                }
            }
        }
    }
}





