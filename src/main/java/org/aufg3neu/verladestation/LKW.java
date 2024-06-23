package org.aufg3neu.verladestation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Die Klasse LKW simuliert einen LKW, der zufällig eine Verladerampe ansteuert, um Container umschlagen zu lassen.
 * Jeder LKW sucht die Verladerampe mit der kürzesten Warteschlange.
 */
public class LKW extends Thread{
    private static final Random RANDOM = new Random();
    private final List<Verladerampe> verladerampen;
    private static final ReentrantLock lock = new ReentrantLock();
    private final int lkwId;

    /**
     * Konstruktor zur Initialisierung des LKWs mit einer Liste von Verladerampen und einer eindeutigen ID.
     *
     * @param verladerampen Die Liste der Verladerampen.
     * @param lkwId         Die eindeutige ID des LKWs.
     */
    public LKW(List<Verladerampe> verladerampen, int lkwId){
        this.verladerampen = verladerampen;
        this.lkwId = lkwId;
    }

    /**
     * Führt die Hauptlogik des LKWs aus. Der LKW wartet eine zufällige Zeit, bevor er eine Verladerampe ansteuert
     * und danach eine weitere zufällige Zeit, bevor er den nächsten Umschlag durchführt.
     */
    @Override
    public void run(){
        int warten = RANDOM.nextInt(3001);
        try {
            sleep(warten);
        } catch (InterruptedException e) {
            System.err.println("Unterbrochen");
        }
        System.out.println("LKW " + this.getLkwId() + " wurde gestartet.");

        while (!isInterrupted()) {
            verladerampen.get(this.kuerzesteWarteschlange()).umschlagen();
            warten = RANDOM.nextInt(5001) + 5000;
            try {
                sleep(warten);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Sucht die Verladerampe mit der kürzesten Warteschlange aus der Liste der Verladerampen.
     *
     * @return Der Index der Verladerampe mit der kürzesten Warteschlange.
     */
    public int kuerzesteWarteschlange(){
        Random random = new Random();
        int index = 0;

        // Sperrt den Zugriff, um Datenkollisionen zu vermeiden
        lock.lock();
        try {
            List<Integer> warteschlangen = new ArrayList<>();
            warteschlangen.add(0);

            // Überprüft die Warteschlange jeder Verladerampe
            for (int i = 1; i < verladerampen.size(); i++) {
                // Aktualisiert den Index, wenn eine kürzere Warteschlange gefunden wird
                if (verladerampen.get(index).getWarteschlange() > verladerampen.get(i).getWarteschlange()) {
                    index = i;
                    warteschlangen.clear();
                    warteschlangen.add(i);
                // Fügt den Index zur Liste hinzu, wenn die Warteschlange gleich lang ist
                } else if (verladerampen.get(index).getWarteschlange() == verladerampen.get(i).getWarteschlange()) {
                    warteschlangen.add(i);
                }
            }
            // Wählt zufällig einen Index aus den Verladerampen mit der kürzesten Warteschlange
            index = warteschlangen.get(random.nextInt(warteschlangen.size()));
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            // Entsperrt den Zugriff, nachdem die Warteschlange gefunden wurde
            lock.unlock();
        }

        return index;
    }

    /**
     * Gibt die eindeutige ID des LKWs zurück.
     *
     * @return Die eindeutige ID des LKWs.
     */
    public long getLkwId() {
        return this.lkwId;
    }
}
