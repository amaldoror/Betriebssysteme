package org.aufg3neu.spiel;

public class Spieler extends Thread{
    private Tisch tisch;
    private int spielerID;

    public Spieler(Tisch tisch, int spielerID){
        this.tisch = tisch;
        this.spielerID = spielerID;
    }
    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            tisch.zug(spielerID);
        }
    }
}
