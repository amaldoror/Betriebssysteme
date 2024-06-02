package org.aufg3.spiel;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    private int choice = -1;            // -1: Tisch ist leer, 0: Schere, 1: Stein, 2: Papier
    private final Lock lock = new ReentrantLock();
    private final Condition tableNotEmpty = lock.newCondition();

    // Methode, um eine Auswahl auf den Tisch zu legen
    public void putChoice(int playerChoice) throws InterruptedException {

        // Schloss erwerben
        lock.lock();
        try {
            // Blockiert den Spieler, wenn der Tisch nicht leer ist
            while (choice != -1) {
                tableNotEmpty.await();
            }
            choice = playerChoice;
            //System.out.println(Thread.currentThread().getName() + " legt " + choiceToString(playerChoice) + " auf den Tisch.");
            tableNotEmpty.signal(); // Weckt den Schiedsrichter auf
        } finally {
            lock.unlock();
        }
    }


    // Methode, um die Auswahl vom Tisch zu nehmen
    public int takeChoice() throws InterruptedException {
        lock.lock();
        try {
            while (choice == -1) {
                tableNotEmpty.await(); // Warten, bis eine Auswahl getroffen wird
            }
            int playerChoice = choice;
            choice = -1;
            tableNotEmpty.signal(); // Weckt den anderen Spieler auf
            return playerChoice;
        } finally {
            lock.unlock();
        }
    }

    // Methode, die die Auswahl in einen String übersetzt
    String choiceToString(int choice) {
        return switch (choice) {
            case 0 -> "Schere";
            case 1 -> "Stein";
            case 2 -> "Papier";
            default -> "Unbekannt";
        };
    }
}
