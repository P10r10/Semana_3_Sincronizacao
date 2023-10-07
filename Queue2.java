public class Queue2 { // thread safe - addressed with synchronized
    private final int MAX = 10000;
    private final int[] queue;
    private int head;
    private int tail;
    private int size;

    public Queue2() {
        queue = new int[MAX];
        head = 0;
        tail = -1;
        size = 0;
    }

    public boolean empty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public int peek() {
        if (empty()) {
            throw new IllegalStateException("Method peek: Queue is empty!");
        }
        return queue[head];
    }

    public synchronized void  offer(int nb) { // added synchronized in this critical block
        if (size == MAX) {
            throw new IllegalStateException("Method offer: Queue is full!");
        }
        tail = (tail + 1) % MAX;
        size++;
        queue[tail] = nb;
    }

    public synchronized int poll() { // added synchronized in this critical block
        if (empty()) {
            throw new IllegalStateException("Method poll: Queue is empty!");
        }
        int toRemove = peek();
        head = (head + 1) % MAX;
        size--;
        return toRemove;
    }

    public void show() {
        if (empty()) {
            System.out.println("Queue is empty!");
        }
        for (int i = 0; i < MAX; i++) {
            System.out.print(queue[(head + i) % MAX] + " ");
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
        Queue2 q = new Queue2();

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

        System.out.println(q.size); // size is now always 6000
    }
}
