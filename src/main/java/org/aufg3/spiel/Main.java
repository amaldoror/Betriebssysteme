package org.aufg3.spiel;

public class Main {
    public static final int NUM_ROUNDS = 10; // Anzahl der Runden
    public static final long SIMUATION_TIME = 10000;

    public static void main(String[] args) {
        Table table = new Table();

        Player player1 = new Player("Spieler 1", table);
        Player player2 = new Player("Spieler 2", table);
        Referee referee = new Referee(table);

        player1.start();
        player2.start();
        referee.start();

        try {
            Thread.sleep(SIMUATION_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            player1.interrupt();
            player2.interrupt();
            referee.interrupt();

            // Warte bis alle Runden gespielt sind
            player1.join();
            player2.join();
            referee.join();

            System.out.println(referee);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}