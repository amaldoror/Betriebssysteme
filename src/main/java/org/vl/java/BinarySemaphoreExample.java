package org.vl.java;

import java.util.concurrent.locks.*;

public class BinarySemaphoreExample {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean locked = false;

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (locked) {
                condition.await();
            }
            locked = true;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            locked = false;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    // Kritischer Abschnitt, der von mehreren Threads aufgerufen wird
    public void criticalSection() throws InterruptedException {
        acquire(); // Semaphor akquirieren, bevor der kritische Abschnitt betreten wird
        try {
            // Kritischer Abschnitt, der durch den Semaphor geschützt ist
            System.out.println(Thread.currentThread().getName() + " enters critical section");
            Thread.sleep(1000); // Simulierte Arbeit
            System.out.println(Thread.currentThread().getName() + " leaves critical section");
        } finally {
            release(); // Semaphor freigeben, nachdem der kritische Abschnitt verlassen wurde
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BinarySemaphoreExample binarySemaphore = new BinarySemaphoreExample();

        // Threads, die den kritischen Abschnitt aufrufen
        Thread thread1 = new Thread(() -> {
            try {
                binarySemaphore.criticalSection();
            } catch (InterruptedException e) {
                System.err.println("BinarySemaphoreExample critical section interrupted");
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                binarySemaphore.criticalSection();
            } catch (InterruptedException e) {
                System.err.println("BinarySemaphoreExample critical section interrupted");
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}

