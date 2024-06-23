package org.aufg3.verladestation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Gueterverkehrszentrum {
    private static final int ANZAHL_VERLADERAMPEN = 5;
    private static final int SIMULATIONSDAUER = 60000; // 60 Sekunden
    private static final int ANZAHL_LKWS = 20;

    public static void main(String[] args) {
        // Hinzufügen der Verladerampen
        List<Verladerampe> verladerampen = new ArrayList<>();
        for (int i = 0; i < ANZAHL_VERLADERAMPEN; i++) {
            verladerampen.add(new Verladerampe(i + 1));
        }

        // Neuer ExecutorService mit einer Anzahl von Threads
        ExecutorService executorService = Executors.newFixedThreadPool(ANZAHL_LKWS);

        // Hinzufügen der LKWs
        List<LKW> lkws = new ArrayList<>();
        for (int i = 0; i < ANZAHL_LKWS; i++) {
            LKW lkw = new LKW(verladerampen);
            lkws.add(lkw);
            executorService.submit(lkw);
        }

        try {
            Thread.sleep(SIMULATIONSDAUER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // LKW
        for (LKW lkw : lkws) {
            lkw.stop();
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("Einige Tasks wurden nicht beendet!");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        for (Verladerampe rampe : verladerampen) {
            System.out.println("Verladerampe " + rampe.getId() + " hat " + rampe.getContainerUmschlaege() + " Container umgeschlagen.");
        }
    }
}

