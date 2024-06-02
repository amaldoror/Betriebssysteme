package org.aufg3.spiel;

public class Referee extends Thread {
    private final Table table;
    private int player1Wins = 0;
    private int player2Wins = 0;
    private int draws = 0;

    public Referee(Table table) {
        this.table = table;
    }

    @Override
    public void start() {
        try {
            for (int i = 0; i < Main.NUM_ROUNDS; i++) {
                int choice1 = table.takeChoice();
                int choice2 = table.takeChoice();

                System.out.println("Schiedsrichter: Spieler 1 wählt " + table.choiceToString(choice1) +
                        ", Spieler 2 wählt " + table.choiceToString(choice2) + ".");

                if (choice1 == choice2)
                    draws++;
                else if ((choice1 == 0 && choice2 == 2) || (choice1 == 1 && choice2 == 0) || (choice1 == 2 && choice2 == 1))
                    player1Wins++;
                else
                    player2Wins++;
            }

            // Ausgabe der Gesamtauswertung
            System.out.println("\nGesamtauswertung:");
            System.out.println("Gesamtanzahl gespielter Runden: " + Main.NUM_ROUNDS);
            System.out.println("Anzahl Unentschieden: " + draws);
            System.out.println("Anzahl Gewinne Spieler 1: " + player1Wins);
            System.out.println("Anzahl Gewinne Spieler 2: " + player2Wins);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
