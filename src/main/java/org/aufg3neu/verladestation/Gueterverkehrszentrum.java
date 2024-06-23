package org.aufg3neu.verladestation;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Gueterverkehrszentrum simuliert den Betrieb eines Güterverkehrszentrums mit Verladerampen und LKWs.
 * Es werden eine bestimmte Anzahl an Verladerampen und LKWs erzeugt und die Simulation läuft für eine definierte Dauer.
 */
public class Gueterverkehrszentrum {
    private static final int ANZAHL_VERLADERAMPEN = 5;
    private static final int SIMULATIONSDAUER = 60000; // 60 Sekunden
    private static final int ANZAHL_LKWS = 20;

    /**
     * Der Haupteinstiegspunkt der Anwendung.
     * Erzeugt Verladerampen und LKWs, startet die Simulation und gibt die Ergebnisse aus.
     *
     * @param args Die Kommandozeilenargumente (werden nicht verwendet).
     */
    public static void main(String[] args) {
        // Fügt die Verladerampen zur Liste hinzu
        List<Verladerampe> verladerampen = new ArrayList<>();
        for (int i = 0; i < ANZAHL_VERLADERAMPEN; i++) {
            verladerampen.add(new Verladerampe(i + 1));
            System.out.println("Verladerampe " + i + " erstellt");
        }

        // Fügt die LKWs zur Liste hinzu
        List<LKW> lkws = new ArrayList<>();
        for (int i = 0; i < ANZAHL_LKWS; i++) {
            LKW lkw = new LKW(verladerampen, i+1);
            lkws.add(lkw);
            lkw.start();
        }

        // Führt die Simulation durch
        try {
            Thread.sleep(SIMULATIONSDAUER);
            System.out.println(Thread.currentThread().getName() + " schläft");
        } catch (InterruptedException e) {
            System.err.println("Simulation unterbrochen");
        }

        // Unterbricht die Threads der LKWs und der Verladerampen
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread instanceof LKW || thread instanceof Verladerampe) {
                thread.interrupt();
                System.out.println(thread.getName() + " unterbrochen");
            }
        }

        // Ergebnisse ausgeben
        System.out.println("Verladestation Ergebnis:");
        for (Verladerampe verladerampe : verladerampen) {
            System.out.println(verladerampe);
        }
    }
}
