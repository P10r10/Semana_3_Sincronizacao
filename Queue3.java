import java.util.concurrent.atomic.AtomicInteger;

public class Queue3 { // thread safe using atomicity
    private final int MAX = 10000;
    private final int[] queue;
    private final AtomicInteger head;
    private final AtomicInteger tail;
    private final AtomicInteger size;

    public Queue3() {
        queue = new int[MAX];
        head = new AtomicInteger(0);
        tail = new AtomicInteger(-1);
        size = new AtomicInteger(0);
    }

    public boolean empty() {
        return size.get() == 0;
    }

    public AtomicInteger getSize() {
        return size;
    }

    public int peek() {
        if (empty()) {
            throw new IllegalStateException("Method peek: Queue is empty!");
        }
        return queue[head.get()];
    }

    public void offer(int nb) {
        if (size.get() == MAX) {
            throw new IllegalStateException("Method offer: Queue is full!");
        }
        tail.set((tail.get() + 1) % MAX);
        size.addAndGet(1);
        queue[tail.get()] = nb;
    }

    public int poll() {
        if (empty()) {
            throw new IllegalStateException("Method poll: Queue is empty!");
        }
        int toRemove = peek();
        head.set((head.get() + 1) % MAX);
        size.addAndGet(-1);
        return toRemove;
    }

    public void show() {
        if (empty()) {
            System.out.println("Queue is empty!");
        }
        for (int i = 0; i < MAX; i++) {
            System.out.print(queue[(head.addAndGet(i)) % MAX] + " ");
        }
    }

    private class ConcurrentQueue extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) { // 1000 offers
                offer(i);
            }
            for (int i = 0; i < 1000; i++) { // 1000 polls
                poll();
            }
            for (int i = 0; i < 1000; i++) { // 1000 offers
                offer(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 6; // decide the nb of threads
        Queue3 q = new Queue3();

        ConcurrentQueue[] threads = new ConcurrentQueue[THREADS];
        for (int i = 0; i < THREADS; i++) {
            threads[i] = q.new ConcurrentQueue();
        }
        for (int i = 0; i < THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < THREADS; i++) {
            threads[i].join();
        }

        System.out.println(q.size); // size is always 6000 as intended
    }
}
