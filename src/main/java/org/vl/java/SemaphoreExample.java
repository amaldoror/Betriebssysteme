package org.vl.java;

import java.util.concurrent.locks.*;

public class SemaphoreExample {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int permits;

    public SemaphoreExample(int initialPermits) {
        if (initialPermits < 0) {
            throw new IllegalArgumentException("Permits must be non-negative");
        }
        this.permits = initialPermits;
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0) {
                condition.await();
            }
            permits--;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            permits++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public int availablePermits() {
        lock.lock();
        try {
            return permits;
        } finally {
            lock.unlock();
        }
    }

    // Kritischer Abschnitt, der von mehreren Threads aufgerufen wird
    public void criticalSection() throws InterruptedException {
        acquire(); // Semaphor akquirieren, bevor der kritische Abschnitt betreten wird
        System.out.println(Thread.currentThread().getName() + " acquired a permit");
        try {
            // Kritischer Abschnitt, der durch den Semaphor geschützt ist
            System.out.println(Thread.currentThread().getName() + " enters critical section");
            Thread.sleep(1000); // Simulierte Arbeit
            System.out.println(Thread.currentThread().getName() + " leaves critical section");
        } finally {
            release(); // Semaphor freigeben, nachdem der kritische Abschnitt verlassen wurde
            System.out.println(Thread.currentThread().getName() + " released a permit");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreExample semaphore = new SemaphoreExample(3);

        // Threads, die den kritischen Abschnitt aufrufen
        Thread thread0 = new Thread(() -> {
            try {
                semaphore.criticalSection();
            } catch (InterruptedException e) {
                System.err.println("Critical section interrupted");
            }
        });

        Thread thread1 = new Thread(() -> {
            try {
                semaphore.criticalSection();
            } catch (InterruptedException e) {
                System.err.println("Critical section interrupted");
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                semaphore.criticalSection();
            } catch (InterruptedException e) {
                System.err.println("Critical section interrupted");
            }
        });

        thread0.start();
        thread1.start();
        thread2.start();

        thread0.join();
        thread1.join();
        thread2.join();

        System.out.println("Available permits after threads finish: " + semaphore.availablePermits());
    }
}
