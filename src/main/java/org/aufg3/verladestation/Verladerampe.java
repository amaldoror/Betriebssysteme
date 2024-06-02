package org.aufg3.verladestation;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Verladerampe {
    private final int id;
    private final AtomicInteger containerUmschlaege = new AtomicInteger(0);
    final Semaphore semaphore = new Semaphore(1, true); // fairness enabled

    public Verladerampe(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getContainerUmschlaege() {
        return containerUmschlaege.get();
    }

    public void umschlagen() {
        try {
            semaphore.acquire();
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
                containerUmschlaege.incrementAndGet();
            } finally {
                semaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

