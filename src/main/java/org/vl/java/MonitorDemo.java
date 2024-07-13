package org.vl.java;

/**
 * <p>Diese Klasse demonstriert das Konzept eines Monitors in Java.</p>
 * <p>Sie zeigt die Verwendung von synchronisierten und nicht-synchronisierten Methoden
 * in einer Multithreading-Umgebung.</p>
 */
public class MonitorDemo {
    private static final int NUM_THREADS = 3;
    private static final int NUM_INCREMENTS = 5;

    private int sharedCounter = 0;
    private final Object lock = new Object();

    /**
     * <p>Die Hauptmethode, die die Demo startet.</p>
     * @param args Kommandozeilenargumente (nicht verwendet)
     * @throws InterruptedException wenn ein Thread unterbrochen wird
     */
    public static void main(String[] args) throws InterruptedException {
        MonitorDemo demo = new MonitorDemo();
        demo.runDemo();
    }

    /**
     * <p>Führt die Demonstration aus, indem mehrere Threads gestartet werden.</p>
     * <p>Zeigt auch die Verwendung der nicht-synchronisierten Methode.</p>
     * @throws InterruptedException wenn ein Thread unterbrochen wird
     */
    private void runDemo() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(new Worker());
            threads[i].start();
        }

        // Demonstriere die nicht-synchronisierte Methode
        for (int i = 0; i < 5; i++) {
            unsynchronizedMethod();
            Thread.sleep(10); // Kurze Pause, um die Ausgabe zu verteilen
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final counter value: " + sharedCounter);
    }

    /**
     * <p>Innere Klasse, die einen Arbeiter-Thread repräsentiert.</p>
     * <p>Jeder Arbeiter inkrementiert den Zähler mehrmals und ruft gelegentlich
     * die nicht-synchronisierte Methode auf.</p>
     */
    private class Worker implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUM_INCREMENTS; i++) {
                incrementCounter();
                if (i % 2 == 0) {
                    unsynchronizedMethod();
                }
            }
        }
    }

    /**
     * <p>Inkrementiert den gemeinsam genutzten Zähler in einem synchronisierten Block.</p>
     * <p>Dies stellt sicher, dass nur ein Thread gleichzeitig auf den Zähler zugreifen kann.</p>
     * <br>
     * <u>Kritischer Abschnitt:</u>
     * <code>
     *     int temp = sharedCounter;
     *     temp++;
     *     sharedCounter = temp;
     * </code>
     */
    private void incrementCounter() {
        synchronized (lock) {
            // Kritischer Abschnitt - nur ein Thread kann hier gleichzeitig sein
            int temp = sharedCounter;
            temp++;
            sharedCounter = temp;
            System.out.println(Thread.currentThread().getName() + " incremented counter to " + sharedCounter);
        }
    }

    /**
     * <p>Eine nicht-synchronisierte Methode zum Vergleich.</p>
     * <p>Diese Methode kann jederzeit von jedem Thread aufgerufen werden,
     * unabhängig vom Zustand des Monitors.</p>
     */
    public void unsynchronizedMethod() {
        System.out.println(Thread.currentThread().getName() + ": Unsynchronized method call");
    }
}