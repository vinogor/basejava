package ru.vinogor;

public class MainDeadlock {

    private static final String LOCK1 = "LOCK-1";
    private static final String LOCK2 = "LOCK-2";

    public static void main(String args[]) {

        deadLock(LOCK1, LOCK2);
        deadLock(LOCK2, LOCK1);
    }

    private static void deadLock(String LOCK1, String LOCK2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " wait " + LOCK1);
            synchronized (LOCK1) {
                System.out.println(Thread.currentThread().getName() + " hold " + LOCK1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "Error: " + e);
                }
                System.out.println(Thread.currentThread().getName() + " wait " + LOCK2);
                synchronized (LOCK2) {
                    System.out.println(Thread.currentThread().getName() + " hold " + LOCK2);
                }
            }
        }).start();
    }
}


/*
    private static class Thread1 extends Thread {
        public void run() {
            synchronized (LOCK2) {
                System.out.println("Thread-2 - Lock2");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Error: " + e);
                }
                System.out.println("Thread-2 - waiting for Lock1");

                synchronized (LOCK1) {
                    System.out.println("Thread-2 - holding Lock1 and Lock2");
                }
            }
        }
    }
*/

