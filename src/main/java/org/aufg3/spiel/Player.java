package org.aufg3.spiel;

import java.util.Random;

public class Player extends Thread {
    private final Table table;
    private final Random random;

    public Player(String name, Table table) {
        super(name);
        this.table = table;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < Main.NUM_ROUNDS; i++) {
                int choice = random.nextInt(3); // Zufällige Auswahl von 0 bis 2
                table.putChoice(choice);
                sleep(1000); // Simuliert Zeit für die Auswahl
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}