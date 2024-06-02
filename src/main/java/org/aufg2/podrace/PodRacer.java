package org.aufg2.podrace;

public class PodRacer {
    public static final int NUM_PODS = 8;
    public static final int NUM_ROUNDS = 3;

    public static void main(String[] args) {


        Thread[] threads = new Thread[NUM_PODS];
        Thread accidentThread = new Thread(new Accident());
        accidentThread.start();
        for (int i = 0; i < NUM_PODS; i++) {
            Pod pod = new Pod(i);
            threads[i] = new Thread(pod);
            threads[i].start();
        }
        try {
            accidentThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
