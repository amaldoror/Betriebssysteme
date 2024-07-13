package org.vl.java;
/**
 * <p><b><u>JoinAndSleep</u></b></p>
 * Die geforderte Aufgabe soll in einer Klasse JoinAndSleep (extends Thread) implementiert werden:<br>
 * Der Konstruktor der Klasse erlaubt es die Schlafzeit zu übergeben und eine Referenz auf einen anderen Thread.
 * Dies ist der Thread auf den gewartet werden soll.<br><br>
 * <code>run()</code> Methode: <br>
 * Diese Methode implementiert die oben genannte Semantik zum Warten und Schlafen.
 * Falls ein Thread gegeben ist, so soll auf sein Ende gewartet werden.
 * Anschließend soll eine bestimmte Zeit geschlafen werden.
 * Fügen Sie zwischen allen Schritten Konsolenausgaben ein, um den Fortschritt zu kontrollieren.
 * Geben Sie hier immer auch den aktuellen Thread aus!<br><br>
 * <code>main()</code> Methode<br>
 * Erzeuge Thread 3: Er soll auf keinen Thread warten und dann4000ms schlafen<br>
 * Erzeuge Thread 2: Er soll auf Thread 3 warten und dann 3000ms schlafen<br>
 * Erzeuge Thread 1: Er soll auf Thread 2 warten und dann 2000ms schlafen<br>
 * Starten Sie Thread 1<br>
 * Starten Sie Thread 2<br>
 * Starten Sie Thread 3<br>
 */
public class JoinAndSleep extends Thread {
    private final int sleepTime; // Zeit, die der Thread schlafen soll
    private final Thread waitForThread; // Referenz auf den Thread, auf den gewartet werden soll

    // Konstruktor, der die Schlafzeit und den zu wartenden Thread erhält
    public JoinAndSleep(int sleepTime, Thread waitForThread) {
        this.sleepTime = sleepTime;
        this.waitForThread = waitForThread;
    }

    @Override
    public void run() {
        try {
            // Wenn ein Thread zum Warten gegeben ist, warte darauf
            if (waitForThread != null) {
                System.out.println("Thread " + this.getId() + " wartet auf Thread " + waitForThread.getId());
                waitForThread.join(); // Warte auf das Ende des anderen Threads
            }

            // Führe den Schlafvorgang aus
            System.out.println("Thread " + this.getId() + " schläft für " + sleepTime + " ms");
            Thread.sleep(sleepTime);

            // Zeige an, dass der Thread aufgewacht ist
            System.out.println("Thread " + this.getId() + " ist aufgewacht");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Die main-Methode startet die Threads entsprechend der Aufgabenstellung
    public static void main(String[] args) {
        // Erzeuge Thread 3: Er soll auf keinen Thread warten und dann 4000ms schlafen
        JoinAndSleep thread3 = new JoinAndSleep(4000, null);

        // Erzeuge Thread 2: Er soll auf Thread 3 warten und dann 3000ms schlafen
        JoinAndSleep thread2 = new JoinAndSleep(3000, thread3);

        // Erzeuge Thread 1: Er soll auf Thread 2 warten und dann 2000ms schlafen
        JoinAndSleep thread1 = new JoinAndSleep(2000, thread2);

        // Starten Sie Thread 1
        thread1.start();

        // Starten Sie Thread 2
        thread2.start();

        // Starten Sie Thread 3
        thread3.start();
    }
}
