package org.vl.java;

public class BarrierDemo {
    private static final int NUMBER_OF_THREADS = 5;
    private static final int STEPS = 5;

    public static void main(String[] args) {
        Barrier barrier = new Barrier(NUMBER_OF_THREADS, () -> System.out.println("Alle Threads haben die Barriere erreicht!"));

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            new Thread(new Worker(i, barrier)).start();
        }
    }

    static class Worker implements Runnable {
        private final int id;
        private final Barrier barrier;

        public Worker(int id, Barrier barrier) {
            this.id = id;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                for (int step = 1; step <= STEPS; step++) {
                    // Simuliere Arbeit
                    int sleepTime = (int) (Math.random() * 1000);
                    Thread.sleep(sleepTime);

                    System.out.printf("Thread %d hat Schritt %d abgeschlossen%n", id, step);

                    // Warte an der Barriere
                    barrier.await();

                    System.out.printf("Thread %d setzt nach der Barriere fort%n", id);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}