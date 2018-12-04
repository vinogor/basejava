package ru.vinogor;

public class MainConcurrency {
        private static final Object Lock1 = new Object();
        private static final Object Lock2 = new Object();

        public static void main(String args[]) {
            Thread1 T1 = new Thread1();
            Thread2 T2 = new Thread2();
            T1.start();
            T2.start();
        }

        private static class Thread1 extends Thread {
            public void run() {
                synchronized (Lock1) {
                    System.out.println("Thread-1 - Lock1");
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {
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
                    }
                    catch (InterruptedException e) {
                        System.out.println("Error: " + e);
                    }
                    System.out.println("Thread-2 - waiting for Lock1");

                    synchronized (Lock1) {
                        System.out.println("Thread-2 - holding Lock1 and Lock2");
                    }
                }
            }
        }


//    public static final int THREADS_NUMBER = 10000;
//    private int counter;
//    private static final Object LOCK = new Object();
//
//    public static void main(String[] args) throws InterruptedException {
//        System.out.println(Thread.currentThread().getName());
//
//        Thread thread0 = new Thread() {
//            @Override
//            public void run() {
//                System.out.println(getName() + ", " + getState());
//                throw new IllegalStateException();
//            }
//        };
//        thread0.start();
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
//            }
//
//            private void inc() {
//                synchronized (this) {
////                    counter++;
//                }
//            }
//
//        }).start();
//
//        System.out.println(thread0.getState());
//
//        final MainConcurrency mainConcurrency = new MainConcurrency();
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
//
//        for (int i = 0; i < THREADS_NUMBER; i++) {
//            Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//            });
//            thread.start();
//            threads.add(thread);
//        }
//
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        System.out.println(mainConcurrency.counter);
//    }
//
//    private synchronized void inc() {
////        synchronized (this) {
////        synchronized (MainConcurrency.class) {
//        counter++;
////                wait();
////                readFile
////                ...
////        }
//    }
//

}