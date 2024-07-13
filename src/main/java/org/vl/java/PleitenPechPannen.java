package org.vl.java;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p><b><u>PleitenPechPannen</u></b></p>
 * Diese Klasse demonstriert unerwünschtes Verhalten beim gleichzeitigen Zugriff auf Objekte.<br>
 * <p><b>Node</b></p>
 * Innere statische Klasse für die einzelnen Objekte.
 * <br>
 */
public class PleitenPechPannen {
    private static class Node {
        String name;
        Node next;

        Node(String name) {
            this.name = name;
        }
    }

    private final Node anker;
    private final AtomicInteger step = new AtomicInteger(0);

    public PleitenPechPannen() {
        anker = new Node("Anker");
        anker.next = new Node("B");
        anker.next.next = new Node("C");
    }

    public void demonstriereProblem() throws InterruptedException {
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(2);

        Thread prozess1 = new Thread(() -> einhaengenVonA(startLatch, endLatch));
        Thread prozess2 = new Thread(() -> aushaengenVonB(startLatch, endLatch));

        prozess1.start();
        prozess2.start();

        startLatch.countDown();
        endLatch.await();

        System.out.println("\nNach der Demonstration:");
        printListe();
    }

    private void einhaengenVonA(CountDownLatch startLatch, CountDownLatch endLatch) {
        try {
            startLatch.await();

            // Schritt 1: Lesen des Ankers
            Node zeigerAufB = anker.next;
            System.out.println("Prozess 1 - Schritt 1: Lesen des Ankers (ZeigerAufB = " + zeigerAufB.name + ")");
            waitForStep(1);

            // Schritt 2: Setzen des NextZeigersA
            Node a = new Node("A");
            a.next = zeigerAufB;
            System.out.println("Prozess 1 - Schritt 2: Setzen des NextZeigersA");
            waitForStep(3);

            // Schritt 3: Setzen des Ankers
            anker.next = a;
            System.out.println("Prozess 1 - Schritt 3: Setzen des Ankers auf A");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            endLatch.countDown();
        }
    }

    private void aushaengenVonB(CountDownLatch startLatch, CountDownLatch endLatch) {
        try {
            startLatch.await();

            // Schritt 1: Lesen des Ankers
            Node zeigerAufB = anker.next;
            System.out.println("Prozess 2 - Schritt 1: Lesen des Ankers (ZeigerAufB = " + zeigerAufB.name + ")");
            waitForStep(2);

            // Schritt 2: Lesen des NextZeigersB
            Node zeigerAufC = zeigerAufB.next;
            System.out.println("Prozess 2 - Schritt 2: Lesen des NextZeigersB (ZeigerAufC = " + zeigerAufC.name + ")");
            waitForStep(4);

            // Schritt 3: Setzen des Ankers
            anker.next = zeigerAufC;
            System.out.println("Prozess 2 - Schritt 3: Setzen des Ankers auf " + zeigerAufC.name);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            endLatch.countDown();
        }
    }

    private void waitForStep(int targetStep) throws InterruptedException {
        while (step.get() < targetStep - 1) {
            Thread.sleep(10);
        }
        step.incrementAndGet();
    }

    private void printListe() {
        Node current = anker;
        while (current != null) {
            System.out.print(current.name + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) throws InterruptedException {
        PleitenPechPannen ppp = new PleitenPechPannen();
        System.out.println("\nAusgangszustand:");
        ppp.printListe();
        System.out.println();
        ppp.demonstriereProblem();
    }
}