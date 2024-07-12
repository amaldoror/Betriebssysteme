package org.vl.java;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAndSet {
    private final AtomicInteger i;

    public TestAndSet() {
        this.i = new AtomicInteger(0);
    }

    public boolean testAndSet() {
        // Atomare Operation: Vergleicht den aktuellen Wert von i mit 0 und setzt ihn auf 1, falls gleich 0
        return i.compareAndSet(0, 1);
    }

    public static void main(String[] args) {
        TestAndSet testAndSet = new TestAndSet();

        // Initialer Wert von i
        System.out.println("Initial value of i:\t\t\t" + testAndSet.i);

        // Erster Aufruf von testAndSet
        boolean result1 = testAndSet.testAndSet();
        System.out.println("First testAndSet result:\t" + result1 + ",\tnew value of i:\t" + testAndSet.i);

        // Zweiter Aufruf von testAndSet
        boolean result2 = testAndSet.testAndSet();
        System.out.println("Second testAndSet result:\t" + result2 + ",\tnew value of i:\t" + testAndSet.i);
    }
}
