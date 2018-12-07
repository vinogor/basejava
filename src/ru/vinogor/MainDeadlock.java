package ru.vinogor;

public class MainDeadlock {

    private static final Object Lock1 = new Object();
    private static final Object Lock2 = new Object();

    public static void main(String args[]) {
        Thread1 T1 = new Thread1();
        Thread1 T2 = new Thread1();
        T1.start();
        T2.start();
    }



    private static class Thread1 extends Thread {
        public void run() {
            synchronized (Lock1) {
                System.out.println("Thread-1 - Lock1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Error: " + e);
                }
                System.out.println("Thread-1 - waiting for Lock2");

                synchronized (Lock2) {
                    System.out.println("Thread-1: Holding Lock1 and Lock2");
                }
            }
        }
    }

    private static class Thread2 extends Thread {
        public void run() {
            synchronized (Lock2) {
                System.out.println("Thread-2 - Lock2");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Error: " + e);
                }
                System.out.println("Thread-2 - waiting for Lock1");

                synchronized (Lock1) {
                    System.out.println("Thread-2 - holding Lock1 and Lock2");
                }
            }
        }
    }
}
