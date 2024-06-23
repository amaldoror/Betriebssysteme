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
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int choice1 = table.takeChoice();
                int choice2 = table.takeChoice();

                System.out.println("Spieler 1 wählt " + table.choiceToString(choice1) +
                        ", Spieler 2 wählt " + table.choiceToString(choice2) + ".");

                if (choice1 == choice2) {
                    System.out.println("Unentschieden.");
                    draws++;
                }
                else if ((choice1 == 0 && choice2 == 2) || (choice1 == 1 && choice2 == 0) || (choice1 == 2 && choice2 == 1)) {
                    System.out.println("Spieler 1 gewinnt.");
                    player1Wins++;
                }
                else{
                    System.out.println("Spieler 2 gewinnt.");
                    player2Wins++;
                }


            } catch (InterruptedException e) {
                //System.err.println("Fehler beim starten der Spieler.");
                //e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public String toString(){
        // Ausgabe der Gesamtauswertung
        return "\nGesamtauswertung:"+
                "\nGesamtanzahl gespielter Runden: " + Main.NUM_ROUNDS +
                "\nAnzahl Unentschieden: " + draws +
                "\nAnzahl Gewinne Spieler 1: " + player1Wins +
                "\nAnzahl Gewinne Spieler 2: " + player2Wins;
    }
}
