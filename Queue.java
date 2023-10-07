public class Queue {
    private final int MAX = 5;
    private final int[] queue;
    private int head;
    private int tail;
    private int size;

    public Queue() {
        queue = new int[MAX];
        head = 0;
        tail = -1;
        size = 0;
    }

    public boolean empty() {
        return size == 0;
    }

    public int peek() {
        if (empty()) {
            throw new IllegalStateException("Method peek: Queue is empty!");
        }
        return queue[head];
    }

    public void offer(int nb) {
        if (size == MAX) {
            throw new IllegalStateException("Method offer: Queue is full!");
        }
        tail = (tail + 1) % MAX;
        size++;
        queue[tail] = nb;
    }

    public int poll() {
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

    public static void main(String[] args) {
        Queue q = new Queue();

    }
}
