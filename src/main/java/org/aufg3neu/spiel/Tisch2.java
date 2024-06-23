package org.aufg3neu.spiel;

import java.util.Random;

public class Tisch2  {
    private final SchereSteinPapier[] zuege;
    public Tisch2(){
        zuege = new SchereSteinPapier[2];
    }

    public synchronized void zug(int spielerID){
        while(zuege[spielerID] != null){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        Random random = new Random();
        zuege[spielerID] = SchereSteinPapier.values()[random.nextInt(3)];
        notifyAll();
    }

    public synchronized int auswertung(){
        while(zuege[0] == null || zuege[1] == null){
            try{
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return 0;
            }
        }
        int ergebnis = -1;
        if(zuege[0] == zuege[1]){
            ergebnis = 0;
        }else{
            ergebnis = switch (zuege[0]) {
                case SCHERE -> (zuege[1] == SchereSteinPapier.PAPIER) ? 1 : 2;
                case STEIN -> (zuege[1] == SchereSteinPapier.SCHERE) ? 1 : 2;
                case PAPIER -> (zuege[1] == SchereSteinPapier.STEIN) ? 1 : 2;
            };
        }

        zuege[0] = null;
        zuege[1] = null;
        notifyAll();
        return ergebnis;
    }
}
