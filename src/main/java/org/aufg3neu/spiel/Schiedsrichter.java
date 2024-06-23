package org.aufg3neu.spiel;

public class Schiedsrichter extends Thread{
    private final Tisch tisch;
    private final int[] spielStand;

    public Schiedsrichter(Tisch tisch){
        this.tisch = tisch;
        spielStand = new int[]{0,0,0};
    }

    @Override
    public void run(){
        while(!isInterrupted()){
            spielStand[tisch.auswertung()]++;
        }
    }

    @Override
    public String toString(){
        return "gespielte Runden:\t" + (spielStand[0] + spielStand[1] + spielStand[2]) +
                "\nUnendschieden:\t\t" + spielStand[0] +
                "\nSpieler1:\t\t\t" + spielStand[1] +
                "\nSpieler2\t\t\t" + spielStand[2];
    }
}
