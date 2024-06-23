package org.aufg3neu.verladestation;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Die Klasse Verladerampe simuliert eine Verladerampe, die Container umschlägt.
 * Jeder Umschlag dauert eine zufällige Zeit und die Klasse hält den Überblick über die Anzahl der umgeschlagenen Container.
 */
public class Verladerampe extends Thread{
    private int containerUmschlaege;
    private final int id;
    private int warteschlange;
    private final ReentrantLock lock;

    /**
     * Konstruktor zur Initialisierung der Verladerampe mit einer eindeutigen ID.
     *
     * @param id Die eindeutige ID der Verladerampe.
     */
    public Verladerampe(int id){
        this.id = id;
        warteschlange = 0;
        containerUmschlaege = 0;
        lock = new ReentrantLock();
    }

    /**
     * Methode zum Umschlagen eines Containers.
     * Erhöht die Warteschlange, schläft für eine zufällige Zeit (simuliert die Bearbeitungszeit) und
     * aktualisiert die Anzahl der umgeschlagenen Container.
     * <p>
     * Nutzt ein ReentrantLock für Thread-Sicherheit.
     */
    public void umschlagen(){
        // Erhöht die Warteschlange, wenn ein neuer Container zum Umschlagen ankommt
        warteschlange++;
        Random random = new Random();

        // Sperrt den Zugriff, um die Umschlagsoperation thread-sicher zu machen
        lock.lock();

        // Zufällige Wartezeit zwischen 0 und 1000 Millisekunden
        long wartezeitMs = random.nextInt(1001);

        try{
            // Simuliert die Bearbeitungszeit durch Schlafen des Threads
            sleep(wartezeitMs);
        } catch (InterruptedException e) {
            // Setzt den Interrupt-Status des Threads zurück und gibt eine Meldung aus
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " unterbrochen");
        }finally {
            this.warteschlange--;
            containerUmschlaege++;

            // Entsperrt den Zugriff, nachdem die Umschlagsoperation abgeschlossen ist
            lock.unlock();
        }
    }

    /**
     * Gibt die aktuelle Warteschlangenlänge zurück.
     *
     * @return Die Länge der Warteschlange.
     */
    public synchronized int getWarteschlange(){
        return warteschlange;
    }

    /**
     * Gibt eine String-Repräsentation der Verladerampe zurück, einschließlich ihrer ID und der Anzahl der umgeschlagenen Container.
     *
     * @return Eine String-Repräsentation der Verladerampe.
     */
    @Override
    public String toString(){
        return "Verladerampe " + id + ": " + containerUmschlaege + " Containerumschläge";
    }
}
