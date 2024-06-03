package org.aufg3.verladestation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LKW implements Runnable {
    private static final Random RANDOM = new Random();
    private final List<Verladerampe> verladerampen;
    private volatile boolean running = true;

    public LKW(List<Verladerampe> verladerampen) {
        this.verladerampen = verladerampen;
    }


    /**
     * Solange die running Flag auf true steht, führt der LKW folgende Tätigkeit durch: <br>
     * Erst wird nach einer freien Verladerampe gesucht. Wenn es eine gibt, wird ein Container umgeschlagen. <br>
     * Der LKW wartet, bis das Umschlagen des Containers durchgeführt wurde.
     */
    @Override
    public void run() {
        while (running) {
            Verladerampe rampe = findVerladerampe();
            if (rampe != null) {
                rampe.umschlagen();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(5000, 10000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Setzt die running Flag auf false. Wird verwendet um LKW Threads zu beenden.
     */
    public void stop() {
        running = false;
    }


    /**
     * Sucht nach einer verfügbaren Verladerampe.
     * @return
     */
    private synchronized Verladerampe findVerladerampe() {
        List<Verladerampe> freieRampen = new ArrayList<>();
        for (Verladerampe rampe : verladerampen) {
            if (rampe.semaphore.availablePermits() > 0) {
                freieRampen.add(rampe);
            }
        }

        if (!freieRampen.isEmpty()) {
            return freieRampen.get(RANDOM.nextInt(freieRampen.size()));
        }

        return null;
    }
}
