package org.example;

public class Thread extends java.lang.Thread {
    private boolean stopThread = false;
    private int timeSleep;
    private String name;
    private int count = 0;
    private int limitCount = 1000;

    public Thread(int timeSleep, String name) {
        this.timeSleep = timeSleep;
        this.name = name;
    }

    public void stopThread() {
        this.stopThread = true;
    }

    public void run() {
        while (!this.stopThread) {
            try {
                if (this.name == "HILO 1") {
                    double progressPercent1 = (double) this.count / this.limitCount * 100;
                    Panel.jpb1.setValue((int) progressPercent1);
                    Panel.lblProgressPercent1.setText((int) progressPercent1 + "%");
                    Panel.jtf1.setText(Integer.toString(this.count));
                } else if (this.name == "HILO 2") {
                    double progressPercent2 = (double) this.count / this.limitCount * 100;
                    Panel.jpb2.setValue((int) progressPercent2);
                    Panel.lblProgressPercent2.setText((int) progressPercent2 + "%");
                    Panel.jtf2.setText(Integer.toString(this.count));
                } else {
                    double progressPercent3 = (double) this.count / this.limitCount * 100;
                    Panel.jpb3.setValue((int) progressPercent3);
                    Panel.lblProgressPercent3.setText((int) progressPercent3 + "%");
                    Panel.jtf3.setText(Integer.toString(this.count));
                }

                this.count++;

                if (this.count > this.limitCount) {
                    Judge.assignPlace(this.name);
                    stopThread();
                    interrupt();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                sleep(this.timeSleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


