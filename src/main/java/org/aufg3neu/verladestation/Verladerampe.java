package org.aufg3neu.verladestation;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Verladerampe extends Thread{
    private int zaehler;
    private final int iD;
    private int warteschlange;
    private final Lock lock;
    public Verladerampe(int iD){
        this.iD = iD;
        warteschlange = 0;
        zaehler = 0;
        lock = new ReentrantLock();
    }

    @Override
    public String toString(){
        return "id: " + iD + ", Verladungen: " + zaehler;
    }
    public synchronized void warteschlangeErhoehen(){
        warteschlange++;
    }
    public synchronized void warteschlangeDekrement(){
        warteschlange--;
    }
    public synchronized int getWarteschlange(){
        return warteschlange;
    }
    public void verladen(){
        warteschlangeErhoehen();
        Random random = new Random();
        lock.lock();
        int warten = random.nextInt(1001);
        try{
            sleep(warten);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }finally {
            this.warteschlangeDekrement();
            zaehler++;
            if(lock != null){
                lock.unlock();
            }
        }
    }
}
