import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter3 { // example solving concurrency problems with lock
    private int count;
    private final Lock lock = new ReentrantLock();

    public Counter3() {
        count = 0;
    }

    public void increment() { // critical block addressed with lock/unlock
        try {
            lock.lock();
            count++;
        } finally {
            lock.unlock();
        }
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
        Counter3 sharedCounter = new Counter3();

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
            test(); // conclusion: 4000 is always the result (after using lock)
        }
    }
}
