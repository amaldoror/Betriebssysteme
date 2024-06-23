package org.aufg2.podrace;

import java.util.Random;

public class Accident implements Runnable {
    @Override
    public void run() {
        try {
            int accidentTime = new Random().nextInt(1000); // Random time for accident to occur
            Thread.sleep(accidentTime);
            //String msg = String.format("Accident occured after %d ms", accidentTime);
            //System.out.println(msg);
            Pod.setAccidentOccurred(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
