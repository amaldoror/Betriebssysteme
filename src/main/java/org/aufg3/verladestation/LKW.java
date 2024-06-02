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

    public void stop() {
        running = false;
    }

    private Verladerampe findVerladerampe() {
        Verladerampe selected = null;
        int minQueueSize = Integer.MAX_VALUE;

        for (Verladerampe rampe : verladerampen) {
            int queueSize = rampe.semaphore.availablePermits() == 0 ? 1 : 0;
            if (queueSize < minQueueSize) {
                minQueueSize = queueSize;
                selected = rampe;
            }
        }

        List<Verladerampe> freieRampen = new ArrayList<>();
        for (Verladerampe rampe : verladerampen) {
            if (rampe.semaphore.availablePermits() > 0) {
                freieRampen.add(rampe);
            }
        }

        if (!freieRampen.isEmpty()) {
            selected = freieRampen.get(RANDOM.nextInt(freieRampen.size()));
        }

        return selected;
    }
}
