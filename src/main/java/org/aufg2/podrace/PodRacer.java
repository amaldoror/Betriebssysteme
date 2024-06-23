package org.aufg2.podrace;

public class
PodRacer {
    public static final int NUM_PODS = 8;
    public static final int NUM_ROUNDS = 3;

    public static void main(String[] args) {

        Thread[] threads = new Thread[NUM_PODS];
        Thread accidentThread = new Thread(new Accident());
        accidentThread.start();

        // Erstellen und Starten der Threads
        for (int i = 0; i < NUM_PODS; i++) {
            Pod pod = new Pod(i);
            threads[i] = new Thread(pod);
            threads[i].start();
        }
        try {
            // Auf Beenden der Threads warten
            for (int i = 0; i < NUM_PODS; i++) {
                threads[i].join();
            }
            accidentThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Threads beenden.
        for (int i = 0; i < NUM_PODS; i++) {
            threads[i].interrupt();
        }
    }
}
