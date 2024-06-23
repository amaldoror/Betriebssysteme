package org.aufg3neu.verladestation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

//erzeugen: warten zwischen 0-3 sekunden
//nach kürzester Schlange gucken
// Aufladen 0-1 sekunden
//zu Zielort fahren 5-10 Sekunden
//nach kürzester Schlange gucken ...

public class LKW extends Thread{
    private final List<Verladerampe> verladerampen;
    private static final ReentrantLock lock = new ReentrantLock();
    public LKW(List<Verladerampe> verladerampen){
        this.verladerampen = verladerampen;
    }

    @Override
    public void run(){
        //warten nach erzeugen
        Random random = new Random();
        int warten = random.nextInt(3001);
        try {
            sleep(warten);
        } catch (InterruptedException e) {
            System.err.println("Unterbrochen");
        }
        System.out.println("LKW: " + this.getName() + " wurde erstellt.");

        //Anzahl an Fahrten
        while (!isInterrupted()) {
            //nach kürzester Schlange gucken und verladen:
            verladerampen.get(this.kuerzesterSchlange()).verladen();
            //zum Zielort fahren:
            warten = random.nextInt(5001) + 5000;
            try {
                sleep(warten);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //System.out.println("verladen erfolgt von: " + this.getName());
        }
    }

    public int kuerzesterSchlange(){
        Random random = new Random();
        int index = 0;

        lock.lock();
        try {
            List<Integer> iGleichlangeSchlange = new ArrayList<>();
            iGleichlangeSchlange.add(0);

            for (int i = 1; i < verladerampen.size(); i++) {
                if (verladerampen.get(index).getWarteschlange() > verladerampen.get(i).getWarteschlange()) {
                    index = i;
                    iGleichlangeSchlange.clear();
                    iGleichlangeSchlange.add(i);
                } else if (verladerampen.get(index).getWarteschlange() == verladerampen.get(i).getWarteschlange()) {
                    iGleichlangeSchlange.add(i);
                }
            }
            index = iGleichlangeSchlange.get(random.nextInt(iGleichlangeSchlange.size()));
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

        return index;
    }
}
