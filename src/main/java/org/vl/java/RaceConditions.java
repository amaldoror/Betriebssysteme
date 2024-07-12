package org.vl.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Dieses Programm demonstriert das sporadische Auftreten von RaceConditions bei zeitgleichem Zugriff auf Objekte.
 *
 */
public class RaceConditions {
    private static final int NUM_EXECUTIONS = 10000;
    private static final int NUM_INCREMENTS = 10000;

    // TODO: Beliebige Anzahl Threads festlegen, die am Wettrennen teilnehmen

    private int counter = 0;

    public static void main(String[] args) {
        int expectedValue = NUM_INCREMENTS * 2;
        List<Integer> actualValues = new ArrayList<>();

        float relativeDifference = 0.0f;

        for (int i = 0; i < NUM_EXECUTIONS; i++) {
            RaceConditions raceConditions = new RaceConditions();

            // Erstellen von Threads, die auf die Methode incrementCounter zugreifen
            Thread thread1 = new Thread(() -> {
                for (int j = 0; j < NUM_INCREMENTS; j++) {
                    raceConditions.incrementCounter();
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int j = 0; j < NUM_INCREMENTS; j++) {
                    raceConditions.incrementCounter();
                }
            });

            // Threads starten
            thread1.start();
            thread2.start();

            // Auf das Ende der Threads warten
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (raceConditions.getCounter() != expectedValue) {
                actualValues.add(raceConditions.getCounter());
            }
            relativeDifference = (float) actualValues.size() / NUM_EXECUTIONS * 100;
        }

        // Nachdem die Threads fertig sind, wird der Wert von counter ausgegeben
        System.out.println("Expected Value:\t\t\t\t" + NUM_INCREMENTS * 2 + "\n"
                + "Number of Executions:\t\t" + NUM_EXECUTIONS + "\n"
                + "Relative Difference:\t\t" + relativeDifference + " %\n"
                + "RaceCondition occured:\t\t" + (!actualValues.isEmpty()) + "\n"
                + "Number of RaceConditions:\t" + actualValues.size() + "\n"
                + "Actual Values:\t\t\t\t" + actualValues);
    }

    // Methode zum Inkrementieren des Zählers
    public void incrementCounter() {
        int temp = counter; // Lese den aktuellen Wert von counter
        temp++;             // Inkrementiere den temporären Wert
        counter = temp;     // Schreibe den inkrementierten Wert zurück in counter
    }

    // Getter für den Zähler
    public int getCounter() {
        return counter;
    }
}
