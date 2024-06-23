package org.aufg3neu.spiel;

public class Main {
    public static void main(String[] args) {

        Tisch tisch = new Tisch();
        Schiedsrichter schiedsrichter = new Schiedsrichter(tisch);
        Spieler spieler1 = new Spieler(tisch, 0);
        Spieler spieler2 = new Spieler(tisch, 1);
        schiedsrichter.start();
        spieler1.start();
        spieler2.start();

        try{
            Thread.sleep(500);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        schiedsrichter.interrupt();
        spieler1.interrupt();
        spieler2.interrupt();

        try {
            schiedsrichter.join();
            spieler1.join();
            spieler2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(schiedsrichter);
    }
}
