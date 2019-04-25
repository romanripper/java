
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class MyThread extends Thread {
    public Random random = new Random();
    private boolean isStoped = false;
    private JTextArea textArea = null;

    public MyThread(JTextArea area) {
        textArea = area;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this) {
                    while (isStoped) {
                        wait();
                    }
                }
                textArea.append(new Date().toString() + "\n");
                Thread.sleep(random.nextInt(1500 - 500) + 500);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void stopThread() {
        isStoped = true;
    }

    public synchronized void resumeThread() {
        isStoped = false;
        notify();
    }

}
