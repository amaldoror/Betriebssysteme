package org.aufg3.spiel;

public class Main {
    public static final int NUM_ROUNDS = 10; // Anzahl der Runden

    public static void main(String[] args) {
        Table table = new Table();
        Player player1 = new Player("Spieler 1", table);
        Player player2 = new Player("Spieler 2", table);
        Referee referee = new Referee(table);

        player1.start();
        player2.start();
        referee.start();

        try {
            // Warte bis alle Runden gespielt sind
            referee.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}