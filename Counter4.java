import java.util.concurrent.atomic.AtomicInteger;

public class Counter4 { // example solving concurrency problems using atomicity
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() { // problem solved with atomic method "incrementAndGet"
        count.incrementAndGet();
    }
    public AtomicInteger getCount() {
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
        Counter4 sharedCounter = new Counter4();

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
            test(); // conclusion: 4000 is always the result (after using atomicity)
        }
    }
}
