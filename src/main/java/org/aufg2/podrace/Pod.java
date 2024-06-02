package org.aufg2.podrace;

import java.util.Random;

import static org.aufg2.podrace.PodRacer.NUM_PODS;
import static org.aufg2.podrace.PodRacer.NUM_ROUNDS;


public class Pod implements Runnable {

    private static final Random random = new Random();
    private static int[] times = new int[NUM_PODS];
    private static int[] positions = new int[NUM_PODS];
    private static boolean[] isFinishedRound = new boolean[NUM_PODS];
    private static volatile boolean accidentOccurred = false;

    private final int podID;
    private int timeTotal;

    public Pod(int podID) {
        this.podID = podID;
        this.timeTotal = 0;
    }

    @Override
    public void run() {
        for (int round = 0; round < NUM_ROUNDS; round++) {
            int timeRound = random.nextInt(100);              // Zufällige Zeit für die Runde
            try {
                Thread.sleep(timeRound);                            // Thread schläft
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (accidentOccurred) {
                //System.out.println("Pod " + podID + " has crashed! Race aborted.");
                return;
            }
            timeTotal += timeRound;
        }
        synchronized (Pod.class) {
            times[podID] = timeTotal;
            isFinishedRound[podID] = true;
            int position = 1;
            for (int i = 0; i < NUM_PODS; i++) {
                if (times[i] < timeTotal && isFinishedRound[i]) {
                    position++;
                }
            }
            positions[podID] = position;
            if (position == NUM_PODS) {
                printResults();
            }
        }
    }

    private void printResults() {
        System.out.println("**** Endstand ****");
        for (int pos = 1; pos <= NUM_PODS; pos++) {
            for (int i = 0; i < NUM_PODS; i++) {
                if (positions[i] == pos) {
                    System.out.println(pos + ". Platz: Pod " + i + " Zeit: " + times[i]);
                }
            }
        }
    }

    public static void setAccidentOccurred(boolean value) {
        accidentOccurred = value;
    }
}





