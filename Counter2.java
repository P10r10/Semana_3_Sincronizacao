public class Counter2 {
    private int count;

    public Counter2() {
        count = 0;
    }

    public synchronized void increment() { // critical block addressed with "synchronized"
        count++;
    }
    public int getCount() {
        return count;
    }

    private class ConcurrentCount extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        }
    }

    public static void test() throws InterruptedException {
        final int THREADS = 4; // decide the nb of threads
        Counter2 sharedCounter = new Counter2();

        ConcurrentCount[] threads = new ConcurrentCount[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threads[i] = sharedCounter.new ConcurrentCount();
        }
        for (int i = 0; i < THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < THREADS; i++) {
            threads[i].join();
        }
        System.out.println(sharedCounter.getCount());
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) { // tests 10 times
            test(); // conclusion: 4000 is not always the result
        }
    }
}
