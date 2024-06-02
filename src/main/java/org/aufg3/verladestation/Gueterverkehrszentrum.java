package org.aufg3.verladestation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Gueterverkehrszentrum {
    private static final int ANZAHL_VERLADERAMPEN = 3;
    private static final int SIMULATIONSDAUER = 60000; // 60 Sekunden

    public static void main(String[] args) {
        List<Verladerampe> verladerampen = new ArrayList<>();
        for (int i = 0; i < ANZAHL_VERLADERAMPEN; i++) {
            verladerampen.add(new Verladerampe(i + 1));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<LKW> lkws = new ArrayList<>();
        for (int i = 0; i < 10; i++) { // Erstellen von 10 LKWs
            LKW lkw = new LKW(verladerampen);
            lkws.add(lkw);
            executorService.submit(lkw);
        }

        try {
            Thread.sleep(SIMULATIONSDAUER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (LKW lkw : lkws) {
            lkw.stop();
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Verladerampe rampe : verladerampen) {
            System.out.println("Verladerampe " + rampe.getId() + " hat " + rampe.getContainerUmschlaege() + " Container umgeschlagen.");
        }
    }
}

